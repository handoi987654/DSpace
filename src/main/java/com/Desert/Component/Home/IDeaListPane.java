package com.Desert.Component.Home;

import com.Desert.Application.IDeaApp;
import com.Desert.Control.Home.HCard;
import com.Desert.Control.Home.HPane;
import com.Desert.Entity.IDea.IDea;
import com.Desert.Service.HomeService;
import com.Desert.Utility.UserData;
import javafx.scene.Scene;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Component
public class IDeaListPane extends HPane {

    @Autowired
    private HomeService homeService;
    @Autowired
    private UserData userData;
    @Autowired
    private ApplicationContext context;

    public IDeaListPane() {
        super();
    }

    @PostConstruct
    private void render() throws IOException {
        List<IDea> iDeaList = homeService.getIDeas();
        for (IDea iDea : iDeaList) {
            IDeaCard card = new IDeaCard(iDea);
            card.setEventHandler(event -> openIDea(iDea));
            addCard(card, 0, 0);
        }
    }

    @SneakyThrows
    private void openIDea(IDea iDea) {
        userData.setIDea(iDea);
        Scene scene = (Scene) context.getBean("scene");
        IDeaApp iDeaApp = (IDeaApp) context.getBean("IDeaApp");
        iDeaApp.load();
        scene.setRoot(iDeaApp);
    }
}

class IDeaCard extends HCard {

    public IDeaCard(IDea iDea) throws IOException {
        setTitle(iDea.getName());
        addInfo("Question", 3, 72, true);
        addInfo("Preparation", 3, 104, false);
        addInfo("Note", 3, 136, false);
        setImage("ghost-rider.png", 256, 168);
        getStyleClass().add("idea-card");
    }
}
