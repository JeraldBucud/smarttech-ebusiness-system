package com.ebusiness.security;

import com.ebusiness.presentation.AuthenticationBean;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Filters access to secured JSF pages.
 *
 * This filter checks whether a user has logged in before allowing access
 * to system function pages. Public pages such as login, registration,
 * email verification, account recovery, password reset, logout, and static
 * JSF resources are allowed without authentication.
 *
 * @author Jerald Christopher Bucud
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Initialises the login filter.
     *
     * @param filterConfig filter configuration
     * @throws ServletException if filter initialisation fails
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialisation logic is required.
    }

    /**
     * Checks whether the requested page is public or requires login.
     *
     * @param request servlet request
     * @param response servlet response
     * @param chain filter chain
     * @throws IOException if redirection or filtering fails
     * @throws ServletException if request processing fails
     */
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String contextPath = httpRequest.getContextPath();
        String requestUri = httpRequest.getRequestURI();

        if (isPublicResource(requestUri, contextPath) || isUserLoggedIn(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        httpResponse.sendRedirect(contextPath + "/login.xhtml");
    }

    /**
     * Releases filter resources.
     */
    @Override
    public void destroy() {
        // No resources need to be released.
    }

    /**
     * Checks whether the requested resource can be accessed without login.
     *
     * @param requestUri requested URI
     * @param contextPath application context path
     * @return true if the resource is public, otherwise false
     */
    private boolean isPublicResource(String requestUri, String contextPath) {

        String path = requestUri.substring(contextPath.length());

        return path.equals("/")
                || path.equals("/index.xhtml")
                || path.equals("/login.xhtml")
                || path.equals("/register.xhtml")
                || path.equals("/emailVerification.xhtml")
                || path.equals("/recoverAccount.xhtml")
                || path.equals("/resetPassword.xhtml")
                || path.equals("/logout.xhtml")
                || path.equals("/favicon.ico")
                || path.startsWith("/jakarta.faces.resource/")
                || path.startsWith("/javax.faces.resource/")
                || path.startsWith("/resources/");
    }

    /**
     * Checks whether the current session has an authenticated user.
     *
     * @param request HTTP request
     * @return true if the user is logged in, otherwise false
     */
    private boolean isUserLoggedIn(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return false;
        }

        AuthenticationBean authenticationBean =
                (AuthenticationBean) session.getAttribute("authenticationBean");

        if (authenticationBean != null && authenticationBean.isLoggedIn()) {
            return true;
        }

        Enumeration<String> attributeNames = session.getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attribute = session.getAttribute(attributeName);

            if (attribute instanceof AuthenticationBean) {
                AuthenticationBean bean = (AuthenticationBean) attribute;
                return bean.isLoggedIn();
            }
        }

        return false;
    }
}