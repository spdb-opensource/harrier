package cn.spdb.harrier.api.model;

import java.util.List;
import cn.spdb.harrier.dao.entity.UdsJobMenu;
public class TreeJobNode {
    /** 子节点. */
    private List<TreeJobNode> children;
    /** 源码. */
    private String code;
    /** id. */
    private String id;
    /** 父节点. */
    private String parentId;
    /** 文本. */
    private String text;
    /** 文本. */
    private String title;
    /** 排序 */
    private String menuRankNo;
    
    private Integer level;
    
    private UdsJobMenu udsJobMenu;
    
    public TreeJobNode(){}
    
	public TreeJobNode(List<TreeJobNode> children, String id, Integer level) {
		super();
		this.children = children;
		this.id = id;
		this.title = id;
		this.level = level;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<TreeJobNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeJobNode> children) {
		this.children = children;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getMenuRankNo() {
		return menuRankNo;
	}
	public void setMenuRankNo(String menuRankNo) {
		this.menuRankNo = menuRankNo;
	}

	public UdsJobMenu getUdsJobMenu() {
		return udsJobMenu;
	}

	public void setUdsJobMenu(UdsJobMenu udsJobMenu) {
		this.udsJobMenu = udsJobMenu;
	}
    
	
}
