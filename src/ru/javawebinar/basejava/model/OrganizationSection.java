package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends Section {
    List<Organization> organizations;

    public OrganizationSection() {
        organizations = new ArrayList<>();
    }


}
