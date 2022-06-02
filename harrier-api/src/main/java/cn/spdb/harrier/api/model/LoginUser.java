package cn.spdb.harrier.api.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.alibaba.fastjson.annotation.JSONField;

import cn.spdb.harrier.dao.entity.MRole;
import cn.spdb.harrier.dao.entity.MUser;

public class LoginUser implements UserDetails {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户唯一标识
	 */
	private String token;

	/**
	 * 登录时间
	 */
	private Long loginTime;

	/**
	 * 过期时间
	 */
	private Long expireTime;

	/**
	 * 登录IP地址
	 */
	private String ipaddr;

	/**
	 * 浏览器类型
	 */
	private String browser;

	/**
	 * 操作系统
	 */
	private String os;

	/**
	 * 权限列表
	 */
	private Set<String> permissions;

	private Set<GrantedAuthority> authoritieSet;

	private MUser mUser;

	public LoginUser(MUser mUser) {
		this.mUser = mUser;
	}

	/**
	 * 用户信息
	 */

	public Long getUserId() {
		return mUser.getUserId();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LoginUser() {
	}

	@JSONField(serialize = false)
	@Override
	public String getPassword() {
		return mUser.getUserPwd();
	}

	@Override
	public String getUsername() {
		return mUser.getUserCode();
	}

	/**
	 * 账户是否未过期,过期无法验证
	 */
	@JSONField(serialize = false)
	@Override
	public boolean isAccountNonExpired() {
		return !mUser.getIsExpired();
	}

	/**
	 * 指定用户是否解锁,锁定的用户无法进行身份验证
	 * 
	 * @return
	 */
	@JSONField(serialize = false)
	@Override
	public boolean isAccountNonLocked() {
		return !mUser.getIsLocked();
	}

	/**
	 * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
	 * 
	 * @return
	 */
	@JSONField(serialize = false)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 是否可用 ,禁用的用户不能身份验证
	 * 
	 * @return
	 */
	@JSONField(serialize = false)
	@Override
	public boolean isEnabled() {
		return mUser.getIsEnable();
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authoritieSet;
	}

	public void addAuthoritie(String authoritie) {
		if (null == authoritieSet) {
			authoritieSet = new HashSet<GrantedAuthority>();
		}
		authoritieSet.add(new GrantedAuthority() {
			private static final long serialVersionUID = 1L;
			private String author = authoritie;

			@Override
			public String getAuthority() {
				return author;
			}
		});
	}

	/**
	 * @param permiss
	 */
	public void addPermissions(String permiss) {
		if (null == permissions) {
			permissions = new HashSet<String>();
		}
		permissions.add(permiss);
	}

}
