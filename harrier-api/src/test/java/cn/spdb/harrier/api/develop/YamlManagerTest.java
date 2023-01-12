package cn.spdb.harrier.api.develop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import cn.spdb.harrier.api.MonitorApplication;
import cn.spdb.harrier.api.controller.develop.JobArrangeController;
import cn.spdb.harrier.api.controller.develop.JobAttributesController;
import cn.spdb.harrier.api.controller.develop.JobDevelopController;
import cn.spdb.harrier.api.model.JobDepRelations;
import cn.spdb.harrier.api.model.JobStepConfig;
import cn.spdb.harrier.api.model.JobYamlObject;
import cn.spdb.harrier.api.service.develop.IDeploySqlService;
import cn.spdb.harrier.api.service.develop.IJobArrangeService;
import cn.spdb.harrier.api.service.develop.IJobAttributesService;
import cn.spdb.harrier.api.service.develop.IJobConfigChangeService;
import cn.spdb.harrier.api.service.develop.IYamlManagerService;
import cn.spdb.harrier.api.utils.FileUtils;
import cn.spdb.harrier.dao.entity.DyJobArrange;
import cn.spdb.harrier.dao.entity.DyJobAttributes;

@SpringBootTest(classes = { MonitorApplication.class })
public class YamlManagerTest {
	@Autowired 
	IJobAttributesService jobAttributesService;
	@Autowired
	IYamlManagerService yamlmanager;
	@Autowired
	IJobArrangeService jobArrangetest;
	@Autowired
	IJobConfigChangeService jobConfigChange;
	@Autowired
	IDeploySqlService deploySqlService;
	
	@Autowired
	JobDevelopController jobDevelopController;
	@Autowired
	JobAttributesController jobAttributesController;
	@Autowired
	JobArrangeController jobArrangeController;
	
	@Test
	public void testYaml() throws IOException {

		DyJobAttributes jobdemo = jobAttributesService.loadById(1L);
		List<JobStepConfig> jobStepList = new ArrayList<JobStepConfig>();
		JobStepConfig jobStep1 = new JobStepConfig();
		jobStep1.setStepNum((byte) 1);
		jobStep1.setOperCmd("python3");
		jobStep1.setScriptPath("$AUTO_HOME/APP/BDP/ADM/BDP_ADM_DTR_DAILY_RPT/bin/bdp_adm_dtr_daily_rpt0100.py");
		jobStep1.setParameter("1 d");
		
		
		JobStepConfig jobStep2 = new JobStepConfig();
		jobStep2.setStepNum((byte) 2);
		jobStep2.setOperCmd("python3");
		jobStep2.setScriptPath("$AUTO_HOME/APP/BDP/ADM/BDP_ADM_DTR_DAILY_RPT/bin/bdp_adm_dtr_daily_rpt0200.py");
		jobStep2.setParameter("1 t");
		
		JobStepConfig jobStep3 = new JobStepConfig();
		jobStep3.setStepNum((byte) 3);
		jobStep3.setOperCmd("python3");
		jobStep3.setScriptPath("$AUTO_HOME/APP/BDP/ADM/BDP_ADM_DTR_DAILY_RPT/bin/bdp_adm_dtr_daily_rpt0300.py");
		jobStep3.setParameter("1 t");
		
		JobStepConfig jobStep4 = new JobStepConfig();
		jobStep4.setStepNum((byte) 4);
		jobStep4.setOperCmd("python3");
		jobStep4.setScriptPath("$AUTO_HOME/APP/BDP/ADM/BDP_ADM_DTR_DAILY_RPT/bin/bdp_adm_dtr_daily_rpt0400.py");
		jobStep4.setParameter("1 t");
		
		jobStepList.add(jobStep1);
		jobStepList.add(jobStep2);
		jobStepList.add(jobStep3);
		jobStepList.add(jobStep4);
		
		List<JobDepRelations> jobDepRelationList = new ArrayList<JobDepRelations>();
		JobDepRelations jobDep1 = new JobDepRelations();
		jobDep1.setDepPlatform("BDP");
		jobDep1.setDepSystem("ABSS");
		jobDep1.setDepJob("BDP_ABSS_LOD_AB_AVERAGE_TIME_F");
		jobDep1.setDepBatch(0);
		
		JobDepRelations jobDep2 = new JobDepRelations();
		jobDep2.setDepPlatform("BDP");
		jobDep2.setDepSystem("ODSX");
		jobDep2.setDepJob("BDP_ODSX_LOD_CRM_R_CORP_BD_CUST_ECHL_HANDFEE_D_F");
		jobDep2.setDepBatch(0);
		
		JobDepRelations jobDep3 = new JobDepRelations();
		jobDep3.setDepPlatform("DEP");
		jobDep3.setDepSystem("BDPF");
		jobDep3.setDepJob("DEP_BDPF_SYN_BDPF_DS_CCMS_ENT_FIXEDASSETS_FULL");
		jobDep3.setDepBatch(1);
		
		JobDepRelations jobDep4 = new JobDepRelations();
		jobDep4.setDepPlatform("KLN");
		jobDep4.setDepSystem("CRM");
		jobDep4.setDepJob("KLN_CRM_ETL_C08_INDIV_PROJECT_CUST_LIST_CUBE");
		jobDep4.setDepBatch(0);
		jobDepRelationList.add(jobDep1);
		jobDepRelationList.add(jobDep2);
		jobDepRelationList.add(jobDep3);
		jobDepRelationList.add(jobDep4);
		
		
		JobYamlObject jobYamlObject = yamlmanager.YamlPretreat(jobdemo, jobStepList, jobDepRelationList);
		yamlmanager.GenerateYaml(jobYamlObject);
		String jobYaml = yamlmanager.JobYaml("./job/" + jobdemo.getJob() + "-" + jobYamlObject.getVersion() + ".yaml");
		
		
		// 测试插入yaml信息到作业编排表
		DyJobArrange dyJobArrange = yamlmanager.createDyJobArrange(jobYamlObject, jobYaml);
		jobArrangetest.insertOneJobArrange(dyJobArrange);
//		
//		// 测试解析yaml文件
//		DyJobArrange loadByJobName = jobArrangetest.loadByJobName(jobdemo.getJob());
//		DyJobAttributes parseDeployYaml = yamlmanager.parseDeployYaml(loadByJobName);
		
		
		/*
		// 正常批量表到yamlobject
		
		JobYamlObject jobYamlObject = jobConfigChange.createJobYamloJobYamlObject("BDP_ADM_DTR_DAILY_RPT", 1);
		yamlmanager.GenerateYaml(jobYamlObject);
		String jobYaml = yamlmanager.JobYaml("./target/BDP_ADM_DTR_DAILY_RPT.yaml");
		DyJobArrange dyJobArrange = yamlmanager.createDyJobArrange(jobYamlObject, jobYaml);
		jobArrangetest.insertOneJobArrange(dyJobArrange);
		DyJobArrange loadByJobName = jobArrangetest.loadByJobName("BDP_ADM_DTR_DAILY_RPT");
		DyJobAttributes parseDeployYaml = yamlmanager.parseDeployYaml(loadByJobName);
		jobAttributesService.createJob(parseDeployYaml);
		*/
		
		
		// sql转换部分
//		String str = "REPLACE INTO `uds_job` (`platform`, `systems`, `job`, `last_status`, `job_date`, `multi_batch`, `call_again_num`, `des`) VALUES( '";
//		System.out.println(str);
//		Map<String, Object> map = new HashMap<String, Object>();
//		JobYamlObject jobYamlObject = jobConfigChange.createJobYamloJobYamlObject("BDP_ADM_DTR_DAILY_RPT", 1);
//		yamlmanager.GenerateYaml(jobYamlObject);
//		Yaml yaml = new Yaml();
//		String jobYaml = yamlmanager.JobYaml("./target/BDP_ADM_DTR_DAILY_RPT.yaml");
//		JobYamlObject load =(JobYamlObject) yaml.load(jobYaml);
//		System.out.println(load.getPlatform());
		
//		DyJobArrange dyJobArrange = jobArrangetest.loadByJobName("BDP_ADM_DTR_DAILY_RPT");
//		deploySqlService.deploySqlGenerate(dyJobArrange);
		
	}
	
//	@Test
	public void DyjobAttributesControllerTest() throws Exception{
		// 获取一个DyjobAttributes对象
		JobYamlObject jobYamlObject = jobConfigChange.createJobYamloJobYamlObject("BDP","ADM","BDP_ADM_DTR_DAILY_RPT", 1);
		yamlmanager.GenerateYaml(jobYamlObject);
		String jobYaml = yamlmanager.JobYaml("./job/project/dev/BDP/ADM/BDP_ADM_DTR_DAILY_RPT/BDP_ADM_DTR_DAILY_RPT-2.yaml");
		DyJobArrange dyJobArrange = yamlmanager.createDyJobArrange(jobYamlObject, jobYaml);
		DyJobAttributes parseDeployYaml = yamlmanager.parseDeployYaml(dyJobArrange);
		
		List<JobStepConfig> jobStepList = new ArrayList<JobStepConfig>();
		JobStepConfig jobStep1 = new JobStepConfig();
		jobStep1.setStepNum((byte) 1);
		jobStep1.setOperCmd("python3");
		jobStep1.setScriptPath("$AUTO_HOME/APP/BDP/ADM/BDP_ADM_DTR_DAILY_RPT/bin/bdp_adm_dtr_daily_rpt0100.py");
		jobStep1.setParameter("1 d");
		
		
		JobStepConfig jobStep2 = new JobStepConfig();
		jobStep2.setStepNum((byte) 2);
		jobStep2.setOperCmd("python3");
		jobStep2.setScriptPath("$AUTO_HOME/APP/BDP/ADM/BDP_ADM_DTR_DAILY_RPT/bin/bdp_adm_dtr_daily_rpt0200.py");
		jobStep2.setParameter("1 t");
		
		JobStepConfig jobStep3 = new JobStepConfig();
		jobStep3.setStepNum((byte) 3);
		jobStep3.setOperCmd("python3");
		jobStep3.setScriptPath("$AUTO_HOME/APP/BDP/ADM/BDP_ADM_DTR_DAILY_RPT/bin/bdp_adm_dtr_daily_rpt0300.py");
		jobStep3.setParameter("1 t");
		
		JobStepConfig jobStep4 = new JobStepConfig();
		jobStep4.setStepNum((byte) 4);
		jobStep4.setOperCmd("python3");
		jobStep4.setScriptPath("$AUTO_HOME/APP/BDP/ADM/BDP_ADM_DTR_DAILY_RPT/bin/bdp_adm_dtr_daily_rpt0400.py");
		jobStep4.setParameter("1 t");
		
		jobStepList.add(jobStep1);
		jobStepList.add(jobStep2);
		jobStepList.add(jobStep3);
		jobStepList.add(jobStep4);
		
		List<JobDepRelations> jobDepRelationList = new ArrayList<JobDepRelations>();
		JobDepRelations jobDep1 = new JobDepRelations();
		jobDep1.setDepPlatform("BDP");
		jobDep1.setDepSystem("ABSS");
		jobDep1.setDepJob("BDP_ABSS_LOD_AB_AVERAGE_TIME_F");
		jobDep1.setDepBatch(0);
		
		JobDepRelations jobDep2 = new JobDepRelations();
		jobDep2.setDepPlatform("BDP");
		jobDep2.setDepSystem("ODSX");
		jobDep2.setDepJob("BDP_ODSX_LOD_CRM_R_CORP_BD_CUST_ECHL_HANDFEE_D_F");
		jobDep2.setDepBatch(0);
		
		JobDepRelations jobDep3 = new JobDepRelations();
		jobDep3.setDepPlatform("DEP");
		jobDep3.setDepSystem("BDPF");
		jobDep3.setDepJob("DEP_BDPF_SYN_BDPF_DS_CCMS_ENT_FIXEDASSETS_FULL");
		jobDep3.setDepBatch(1);
		
		JobDepRelations jobDep4 = new JobDepRelations();
		jobDep4.setDepPlatform("KLN");
		jobDep4.setDepSystem("CRM");
		jobDep4.setDepJob("KLN_CRM_ETL_C08_INDIV_PROJECT_CUST_LIST_CUBE");
		jobDep4.setDepBatch(0);
		jobDepRelationList.add(jobDep1);
		jobDepRelationList.add(jobDep2);
		jobDepRelationList.add(jobDep3);
		jobDepRelationList.add(jobDep4);
		
//		String add = jobAttributesController.add(parseDeployYaml, jobStepList, jobDepRelationList);
//		
//		Page<DyJobArrange> page1 = new Page<DyJobArrange>(1,2);
//		Page<DyJobArrange> findAllJobVersion = jobArrangetest.findAllJobVersion("BDP_ADM_DTR_DAILY_RPT1", page1);
//		System.out.println(findAllJobVersion.toString());
//		
//		List<DyJobArrange> findAllJobVersion2 = jobArrangeController.findAllJobVersion(page1, "BDP_ADM_DTR_DAILY_RPT1");
//		
//		Map<String, Object> loadJobArranges = jobArrangeController.loadJobArranges(1L);
	}
	
	@Test
	public void compressTest() throws IOException {
		// 压缩
		File[] files = new File[1];
		files[0] = new File("./job/");
		File file = new File("./target/job.tar");
		String absolutePath = file.getAbsolutePath();
//		FileUtils.doArchiver(files, absolutePath);
		
		// 解压
		FileUtils.doUnArchiver(file, "./target/","");
		
		
	}
}
