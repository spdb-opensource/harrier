package cn.spdb.harrier.api.service.develop.impl;

import java.sql.Connection;
import java.util.List;
import java.sql.Statement;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import cn.spdb.harrier.api.model.JobYamlObject;
import cn.spdb.harrier.api.service.develop.IDeploySqlCreate;
import cn.spdb.harrier.api.service.develop.IDeploySqlService;
import cn.spdb.harrier.api.service.develop.IJobArrangeService;
import cn.spdb.harrier.api.utils.JobDeployStatus;
import cn.spdb.harrier.dao.datasource.SpringDruidFactory;
import cn.spdb.harrier.dao.entity.DyJobArrange;

@Service
public class DepploySqlServiceImpl implements IDeploySqlService{
	
	// 获取sqlSession
	@Autowired
	private SpringDruidFactory springDruidFactory;
	@Autowired
	private IDeploySqlCreate deploySqlCreate;
	@Autowired
	private IJobArrangeService jobArrangeService;

	@Override
	public boolean deploySqlGenerate(DyJobArrange dyJobArrange) {
	
		if(null != dyJobArrange) {
			// 作业配置信息
			String jobYamlConfig = dyJobArrange.getDeployYaml();
			Yaml yaml = new Yaml();
			JobYamlObject jobYamlObject = yaml.loadAs(jobYamlConfig, JobYamlObject.class);
			
			// 根据变更状态生成sql
			switch(dyJobArrange.getTaskStatus()) {
			case 1:
				List<String> newJobSql = deploySqlCreate.newJobSql(jobYamlObject);
				if(newJobSql.size() > 0) {
					int[] sqlExecute = sqlExecute(newJobSql);
					for(int i=0; i<newJobSql.size(); i++) {
						if(sqlExecute[i] > 0) {
							System.out.println("Sql: " + newJobSql.get(i) + " 执行成功，影响了" + sqlExecute[i] + "行数据;");
						}else if(sqlExecute[i] == Statement.SUCCESS_NO_INFO) {
							System.out.println("Sql: " + newJobSql.get(i) + " 执行成功，影响的行数未知;");
						}else if(sqlExecute[i] == Statement.EXECUTE_FAILED) {
							System.out.println("Sql: " + newJobSql.get(i) + " 执行失败;");
						}
					}
					return true;
				}
				break;
			case 2:
				List<String> updateJobSql = deploySqlCreate.updateJobSql(jobYamlObject);
				if(updateJobSql.size() > 0) {
					int[] sqlExecute = sqlExecute(updateJobSql);
					for(int i=0; i<updateJobSql.size(); i++) {
						if(sqlExecute[i] > 0) {
							System.out.println("Sql: " + updateJobSql.get(i) + " 执行成功，影响了" + sqlExecute[i] + "行数据;");
						}else if(sqlExecute[i] == Statement.SUCCESS_NO_INFO) {
							System.out.println("Sql: " + updateJobSql.get(i) + " 执行成功，影响的行数未知;");
						}else if(sqlExecute[i] == Statement.EXECUTE_FAILED) {
							System.out.println("Sql: " + updateJobSql.get(i) + " 执行失败;");
						}
					}
					return true;
				}
				break;
			case 3:
				List<String> deleteJobSql = deploySqlCreate.deleteJobSql(jobYamlObject);
				if(deleteJobSql.size() > 0) {
					int[] sqlExecute = sqlExecute(deleteJobSql);
					for(int i=0; i<deleteJobSql.size(); i++) {
						if(sqlExecute[i] > 0) {
							System.out.println("Sql: " + deleteJobSql.get(i) + " 执行成功，影响了" + sqlExecute[i] + "行数据;");
						}else if(sqlExecute[i] == Statement.SUCCESS_NO_INFO) {
							System.out.println("Sql: " + deleteJobSql.get(i) + " 执行成功，影响的行数未知;");
						}else if(sqlExecute[i] == Statement.EXECUTE_FAILED) {
							System.out.println("Sql: " + deleteJobSql.get(i) + " 执行失败;");
						}
					}
					return true;
				}
				break;
			}
		}
		return false;
	}
	
	@Override
	public int[] sqlExecute(List<String> contextList) {
		try {
			SqlSession sqlSession = springDruidFactory.sqlSessionFactory().openSession();
			
			// 获取connection
			Connection connection = sqlSession.getConnection();
			connection.setAutoCommit(false);
			
			Statement sm = null;			
			sm = connection.createStatement();
			
			for(int i = 0; i < contextList.size() ; i++) {
				System.out.println("sql语句为： " + contextList.get(i));
				sm.addBatch(contextList.get(i));
			}
			// 批量提交
			int[] executeBatch = sm.executeBatch();
			connection.commit();
			connection.close();
			return executeBatch;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean rollBack(DyJobArrange dyJobArrange) {
		// 对于新增作业执行下线
		if(dyJobArrange.getTaskStatus() == JobDeployStatus.UPDATE_ADD.getValue()) {
			return deploySqlGenerate(dyJobArrange);	
		} else {
			// 获取变更或者下线作业的上一版本的作业编排信息
			DyJobArrange oldJobArrange = jobArrangeService.loadByJobVersion(dyJobArrange.getPlatform(), dyJobArrange.getSystems(), dyJobArrange.getJob(), (dyJobArrange.getVersion()-1));
			if(null != oldJobArrange) {
				oldJobArrange.setTaskStatus(JobDeployStatus.UPDATE_CHANGE.getValue());
				return deploySqlGenerate(oldJobArrange);
			}
			// 没有可以回退的版本
			return false;
		}
	}

}
