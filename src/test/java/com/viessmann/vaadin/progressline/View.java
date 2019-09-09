package com.viessmann.vaadin.progressline;

import com.bkb.vaadin.progressline.ProgressLine;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("")
public class View extends Div {

    public View() {
        ProgressLine progressLine = new ProgressLine();
        add(progressLine);
    }
}
