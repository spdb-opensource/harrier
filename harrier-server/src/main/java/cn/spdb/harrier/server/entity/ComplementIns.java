package cn.spdb.harrier.server.entity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cn.spdb.harrier.common.model.DAG;
import cn.spdb.harrier.common.utils.Symbol;
import cn.spdb.harrier.dao.entity.UdsComplement;
import cn.spdb.harrier.dao.entity.UdsJobDependency;

public class ComplementIns {

	private DAG<String, JobExecutionContext, Integer> dag = new DAG<String, JobExecutionContext, Integer>();

	private UdsComplement udsComplement;

	private List<String[]> jobList;


	public ComplementIns(List<String[]> jobList) {
		this.jobList = jobList;
	}

	public List<String[]> getJobList() {
		return jobList;
	}

	public void setJobList(List<String[]> jobList) {
		this.jobList = jobList;
	}

	public LocalDateTime getStartTime() {
		return udsComplement.getStartTime();
	}

	public LocalDateTime getEndTime() {
		return udsComplement.getEndTime();
	}

	public Collection<JobExecutionContext> getBeginJob() {
		return dag.getBeginNode().stream().map(map -> dag.getNode(map)).collect(Collectors.toList());
	}

	public Collection<JobExecutionContext> getEndJob() {
		return dag.getEndNode().stream().map(map -> dag.getNode(map)).collect(Collectors.toList());
	}

	public boolean addEdgeJob(UdsJobDependency dependency) {
		String fromNode = dependency.getDepPlatform() + Symbol.XIA_HUA_XIAN + dependency.getDepSystem()
				+ Symbol.XIA_HUA_XIAN + dependency.getDepJob();
		String toNode = dependency.getPlatform() + Symbol.XIA_HUA_XIAN + dependency.getSystems() + Symbol.XIA_HUA_XIAN
				+ dependency.getJob();
		return dag.addEdge(fromNode, toNode, dependency.getDepBatch(), false);
	}

	public void addNodeInfoJob(JobExecutionContext jobExecutionContext) {
		dag.addNode(jobExecutionContext.getJobId(), jobExecutionContext);
	}

	public Collection<JobExecutionContext> getSubsequent(String node) {
		return dag.getSubsequentNodes(node).stream().map(map -> dag.getNode(map)).collect(Collectors.toList());
	}

	public Collection<JobExecutionContext> getPrevious(String node) {
		return dag.getPreviousNodes(node).stream().map(map -> dag.getNode(map)).collect(Collectors.toList());
	}

	public Integer getPreviousEdgeInfo(String snode, String tnode) {
		return dag.getPreviousEdgeInfo(snode, tnode);

	}

	public Integer getSubsequentEdgeInfo(String snode, String tnode) {
		return dag.getSubsequentEdgeInfo(snode, tnode);
	}

	public Long getComplementId() {
		return udsComplement.getId();
	}

	public UdsComplement getUdsComplement() {
		return udsComplement;
	}

	public void setUdsComplement(UdsComplement udsComplement) {
		this.udsComplement = udsComplement;

	}

	public List<Integer> getbatchList() {
		return Arrays.stream(udsComplement.getBatchRange().split(Symbol.DOU_HAO_REG)).flatMapToInt(mapper -> {
			if (mapper.matches("[0-9]+")) {
				return IntStream.of(Integer.valueOf(mapper));
			} else if (mapper.matches("^[0-9]+-[0-9]+$")) {
				String[] intStr = mapper.split(Symbol.HENG_GANG);
				int min = Integer.valueOf(intStr[0]);
				int max = Integer.valueOf(intStr[1]);
				int size = max - min + 1;
				return size > 0 ? IntStream.iterate(min, f -> {
					return ++f;
				}).limit(size) : null;
			} else {
				return null;
			}
		}).boxed().sorted().collect(Collectors.toList());
	}

	public DAG<String, JobExecutionContext, Integer> getDag() {
		return dag;
	}

	public void setDag(DAG<String, JobExecutionContext, Integer> dag) {
		this.dag = dag;
	}

	
}
