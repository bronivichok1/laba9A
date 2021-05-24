package com.company;



import java.io.IOException;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import com.company.User;
import com.company.UserList;
import com.company.UserList.UserExistsException;
import com.company.UserListHelper;

public class AddUser extends SimpleTagSupport {

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void doTag() throws JspException, IOException {

        String errorMessage = null;

        UserList userList = (UserList)getJspContext().getAttribute("users", PageContext.APPLICATION_SCOPE);

        if (user.getLogin()==null || user.getLogin().equals("")) {
            errorMessage = "Логин не может быть пустым!";
        } else {

            if (user.getName()==null || user.getName().equals("")) {
                errorMessage = "Имя пользователя не может быть пустым!";
            }
        }

        if (errorMessage==null) {
            try {

                userList.addUser(user);

                UserListHelper.saveUserList(userList);
            } catch (UserExistsException e) {

                errorMessage = "Пользователь с таким логином уже существует!";
            }
        }

        getJspContext().setAttribute("errorMessage", errorMessage,
                PageContext.SESSION_SCOPE);
    }
}
