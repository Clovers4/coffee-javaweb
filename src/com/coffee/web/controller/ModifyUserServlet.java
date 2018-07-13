package com.coffee.web.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import com.coffee.domain.User;
import com.coffee.service.IUserService;
import com.coffee.service.impl.UserServiceImpl;
import com.coffee.util.WebUtils;
import com.coffee.web.formbean.ModifyUserFormBean;

@WebServlet(name = "ModifyUserServlet", urlPatterns = "/servlet/modifyUserServlet")
public class ModifyUserServlet extends HttpServlet {

	private IUserService userService = new UserServiceImpl();
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ModifyUserFormBean formBean = WebUtils.requestToBean(request, ModifyUserFormBean.class);
		System.out.println("------------ModifyUserServlet work start-----------");
		System.out.println(formBean);

		modify(request, response, formBean);

		request.setAttribute("operateSuccess", "修改成功！！");
		request.getRequestDispatcher("/pages/admin/manage-users.jsp").forward(request, response);
	}

	private void modify(HttpServletRequest request, HttpServletResponse response, ModifyUserFormBean formBean)
			throws ServletException, IOException {
		User user = new User();
		try {
			ConvertUtils.register(new DateLocaleConverter(), Date.class);// 注册字符串到日期的转换器
			BeanUtils.copyProperties(user, formBean);
			System.out.println(user);

			userService.update(user);
		} catch (Exception e) {
			request.setAttribute("operateError", "操作失败！！");
			request.getRequestDispatcher("/pages/admin/manage-users.jsp").forward(request, response);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
