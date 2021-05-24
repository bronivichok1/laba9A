package com.company;

import java.io.IOException;
import java.util.Calendar;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import com.company.Ad;
import com.company.AdList;
import com.company.AdListHelper;
import com.company.User;

public class UpdateAd extends SimpleTagSupport {

    private Ad ad;

    public void setAd(Ad ad) {
        this.ad = ad;
    }
    public void doTag() throws JspException, IOException {

        String errorMessage = null;

        AdList adList = (AdList) getJspContext().getAttribute("ads",
                PageContext.APPLICATION_SCOPE);

        User currentUser = (User) getJspContext().getAttribute("authUser",
                PageContext.SESSION_SCOPE);

        if (ad.getSubject()==null || ad.getSubject().equals("")) {
            errorMessage = "Заголовок не может быть пустым!";
        } else {

            if (currentUser==null || (ad.getId()>0 &&
                    ad.getAuthorId()!=currentUser.getId())) {

                errorMessage = "Вы пытаетесь изменить сообщение, к которому не обладаете правами доступа!";
            }
        }

        if (errorMessage==null) {

            ad.setLastModified(Calendar.getInstance().getTimeInMillis());

            if (ad.getId()==0) {
                adList.addAd(currentUser, ad);
            } else {

                adList.updateAd(ad);
            }

            AdListHelper.saveAdList(adList);
        }

        getJspContext().setAttribute("errorMessage", errorMessage,
                PageContext.SESSION_SCOPE);
    }
}
