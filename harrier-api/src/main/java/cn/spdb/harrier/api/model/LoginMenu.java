package cn.spdb.harrier.api.model;

import java.util.List;
import java.util.Map;

public class LoginMenu{
	/** id. */
	private Integer id;
	/** 父节点. */
	private Integer parentId;
	/** 路径. */
	private String path;
	/** 名称. */
	private String name;
	/** meta. */
	private LoginMenuMeta meta;
	/** 子节点. */
	private List<LoginMenu> children;
	/** 排序 */
	private Integer menuRankNo;
	
	private String component;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<LoginMenu> getChildren() {
		return children;
	}
	public void setChildren(List<LoginMenu> children) {
		this.children = children;
	}
	public LoginMenuMeta getMeta() {
		return meta;
	}
	public void setMeta(LoginMenuMeta meta) {
		this.meta = meta;
	}
	public Integer getMenuRankNo() {
		return menuRankNo;
	}
	public void setMenuRankNo(Integer menuRankNo) {
		this.menuRankNo = menuRankNo;
	}
	
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((component == null) ? 0 : component.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((menuRankNo == null) ? 0 : menuRankNo.hashCode());
		result = prime * result + ((meta == null) ? 0 : meta.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginMenu other = (LoginMenu) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (component == null) {
			if (other.component != null)
				return false;
		} else if (!component.equals(other.component))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (menuRankNo == null) {
			if (other.menuRankNo != null)
				return false;
		} else if (!menuRankNo.equals(other.menuRankNo))
			return false;
		if (meta == null) {
			if (other.meta != null)
				return false;
		} else if (!meta.equals(other.meta))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	
	
	
}
