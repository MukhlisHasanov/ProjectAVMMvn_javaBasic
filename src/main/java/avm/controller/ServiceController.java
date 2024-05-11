package avm.controller;

import avm.products.Personal;
import avm.products.PersonalState;

public class ServiceController {
    private Personal personal;

    public ServiceController(Personal personal) {
        this.personal = personal;
    }

    public void run() {
        PersonalState department = personal.getDepartment();

        switch (department) {
            case ADMIN:
                break;
            case MARKET:
                break;
            case CAFE:
                break;
            case CLOTH:
                break;
            case MOVIE:
                break;
        }
    }
}
