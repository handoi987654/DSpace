package com.Desert.Service;

import com.Desert.Entity.Project.Project;
import com.Desert.Entity.Project.ProjectNote;

import java.util.List;

public interface ProjectService {

    List<ProjectNote> getNoteList(Project project);
}
