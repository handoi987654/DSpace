package com.Desert.Utility;

import com.Desert.Entity.IDea.IDea;
import com.Desert.Entity.Project.Project;
import javafx.application.HostServices;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UserData {

    private HostServices hostServices;
    private IDea iDea;
    private Project project;
}
