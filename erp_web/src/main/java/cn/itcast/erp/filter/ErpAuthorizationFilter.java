package cn.itcast.erp.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class ErpAuthorizationFilter extends AuthorizationFilter {

	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws IOException {

		Subject subject = getSubject(request, response);
		String[] perms = (String[]) mappedValue;// 权限资源

		boolean isPermitted = false;// 通行
		if (null != subject && perms.length > 0) {
			for (String perm : perms) {
				if (subject.isPermitted(perm)) {
					isPermitted = true;
				};
			}
		} else {
			isPermitted = true;
		}

		return isPermitted;
	}

}
