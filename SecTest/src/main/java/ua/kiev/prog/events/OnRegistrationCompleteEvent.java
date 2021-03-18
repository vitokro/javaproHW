package ua.kiev.prog.events;

import org.springframework.context.ApplicationEvent;
import ua.kiev.prog.model.CustomUser;


public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private CustomUser user;

    public OnRegistrationCompleteEvent(
            CustomUser user, String appUrl) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

}
