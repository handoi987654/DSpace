package com.Desert.Repository;

import com.Desert.Entity.IDea.IDea;
import com.Desert.Entity.Project.Project;

import java.util.List;

public interface HomeRepo {

    List<Project> getProjects();

    void insertProject(Project project);

    List<IDea> getIDeas();

    void insertIDea(IDea iDea);
}
