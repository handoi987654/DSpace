package com.Desert.Repository;

import com.Desert.Entity.Project.Project;
import com.Desert.Entity.Project.ProjectNote;

import java.util.List;

public interface ProjectRepo {

    List<ProjectNote> getNoteList(Project project);
}
