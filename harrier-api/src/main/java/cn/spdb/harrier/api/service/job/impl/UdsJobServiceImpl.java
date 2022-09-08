package cn.spdb.harrier.api.service.job.impl;

import cn.spdb.harrier.api.model.TreeJobNode;
import cn.spdb.harrier.api.service.job.UdsJobService;
import cn.spdb.harrier.dao.entity.UdsJob;
import cn.spdb.harrier.dao.entity.UdsJobMenu;

import cn.spdb.harrier.dao.mapper.UdsJobMapper;
import cn.spdb.harrier.dao.mapper.UdsJobMenuMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Service
public class UdsJobServiceImpl implements UdsJobService {
    @Autowired
    private UdsJobMapper mapper;
    @Autowired
    private UdsJobMenuMapper mapper2;
    /**
     *新增
     */
    @Override
    public int add(UdsJob record) {
        return mapper.insertSelective( record );
    }

    /**
     *id删除
     */
    @Override
    public int delete(Long id) {
        return mapper.deleteByPrimaryKey( id );
    }

    /**
     *修改
     */
    @Override
    public int update(UdsJob record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     *查看详情
     */
    @Override
    public UdsJob getDetail(Long id) {
        return mapper.selectByPrimaryKey( id ).orElse(null);
    }

    /**
     *列表查询
     */
    @Override
    public List<UdsJob> listQuery() {
        return mapper.select( null );
    }
    
    /**
     * 作业管理—查询所有作业(分页)
     */
	@Override
	public Page<UdsJobMenu> selectAll(Page<UdsJobMenu> page,String platform,String systems,String job,String last_status) {
		return mapper2.selectAll(page,platform,systems,job,last_status);
	}
	

	/**
	 * 作业列表-查询作业详情	
	 */
	@Override
	public UdsJobMenu selectJobDetail(String platform, String systems, String job) {
		return mapper2.selectJobDetail(platform, systems, job);
	}

	@Override
	public List<TreeJobNode> listAllUpJobs(String job, Integer level) {
		List<UdsJobMenu> jobList = mapper2.listAllUpJobs(new String[]{job});
		String[] jobNames = new String[jobList != null ? jobList.size():0];	
		List<TreeJobNode> treeJobNodes = new ArrayList<TreeJobNode>();
		TreeJobNode treeJobNode = null;
		for (int i = 0, size = jobList.size(); i < size; i++) {
			UdsJobMenu udsJobMenu = jobList.get(i);
			treeJobNode = new TreeJobNode(new ArrayList<TreeJobNode>(), udsJobMenu.getJob(), 1);
			treeJobNode.setUdsJobMenu(udsJobMenu);
			treeJobNodes.add(treeJobNode);
			jobNames[i] = udsJobMenu.getJob();
		}
		treeJobNode = null;
		for (int i = 1; i < level; i++) {
			if (jobNames.length == 0) {
				break;
			}
			jobList = mapper2.listAllUpJobs(jobNames);
			jobNames = new String[jobList != null ? jobList.size():0];
			if (jobNames.length == 0) {
				break;
			}
			for (int j = 0, size = jobList.size(); j < size; j++) {
				UdsJobMenu udsJobMenu = jobList.get(j);
				jobNames[j] = udsJobMenu.getJob();
				handleTreeNode(udsJobMenu, treeJobNodes, i, null);
			}
		}
		return treeJobNodes;
	}

	@Override
	public List<TreeJobNode> listAllDownjobs(String job, Integer level) {
		List<UdsJobMenu> jobList = mapper2.listAllDownjobs(new String[]{job});
		String[] jobNames = new String[jobList != null ? jobList.size():0];
		List<TreeJobNode> treeJobNodes = new ArrayList<TreeJobNode>();
		TreeJobNode treeJobNode = null;
		for (int i = 0, size = jobList.size(); i < size; i++) {
			UdsJobMenu udsJobMenu = jobList.get(i);
			treeJobNode = new TreeJobNode(new ArrayList<TreeJobNode>(), udsJobMenu.getJob(), 1);
			treeJobNode.setUdsJobMenu(udsJobMenu);
			treeJobNode.setText("1");
			treeJobNodes.add(treeJobNode);
			jobNames[i] = udsJobMenu.getJob();
		}
		treeJobNode = null;
		for (int i = 1; i < level; i++) {
			if (jobNames.length == 0) {
				break;
			}
			jobList = mapper2.listAllDownjobs(jobNames);
			jobNames = new String[jobList != null ? jobList.size():0];
			if (jobNames.length == 0) {
				break;
			}
			for (int j = 0, size = jobList.size(); j < size; j++) {
				UdsJobMenu udsJobMenu = jobList.get(j);
				jobNames[j] = udsJobMenu.getJob();
				handleTreeNode(udsJobMenu, treeJobNodes, i, jobList);
			}
		}
		return treeJobNodes;
	}
	
	private void handleTreeNode(UdsJobMenu udsJobMenu, List<TreeJobNode> treeJobNodes, int i, List<UdsJobMenu> jobStmList) {
		if (treeJobNodes == null || treeJobNodes.size() == 0) {
			return;
		}
		TreeJobNode treeJobNode = null;
		for (TreeJobNode tjn : treeJobNodes) {
			if (udsJobMenu.getPjob().equals(tjn.getId()) && tjn.getLevel() == i) {
				treeJobNode = new TreeJobNode(new ArrayList<TreeJobNode>(), udsJobMenu.getJob(), i + 1); 
				treeJobNode.setUdsJobMenu(udsJobMenu);
				if(jobStmList != null) {
					for (UdsJobMenu stmJob : jobStmList) {
						if (stmJob.getPjob().equals(tjn.getId()) && 
								udsJobMenu.getJob().equals(stmJob.getJob())) {
							treeJobNode.setText("1");
							break;
						}
					}
				}
				tjn.getChildren().add(treeJobNode);
			}
			handleTreeNode(udsJobMenu, tjn.getChildren(), i, null);
		}
	}

	@Override
	public int updateJobDetail(UdsJobMenu udsJobMenu) {
		return mapper2.updateJobDetail(udsJobMenu);
	}

	@Override
	public int invokeJob(String platform, String systems, String job, LocalDate jobdate, Integer multibatch) {
        //手动置pending，streamtype= 5
    	Byte streamtype= 5;
		return mapper.invokeJob(platform, systems, job, jobdate, multibatch,streamtype);
	}

	@Override
	public Page<UdsJobMenu> listUpJobs(Page<UdsJobMenu> page, String job) {
		return mapper2.listUpJobs(page, job);
	}

	
}