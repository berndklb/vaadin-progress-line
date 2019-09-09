package com.bkb.vaadin.progressline;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.binder.HasItems;

@CssImport("./styles/progress-line.css")
public class ProgressLine extends Composite<FlexLayout> implements HasItems<String>  
//HasElement
//, HasEnabled
//, HasItems<String>
//, HasOrderedComponents<TextField>
//, HasSize
//, HasStyle
//, HasText
{
	private LinkedList<String> items;
	private String active;
	private LinkedList<ItemLabel> itemLabels = new LinkedList<>();
	private FlexLayout progressLayout;
	private Span lblHeadline;
	
	public ProgressLine() {
		this(null);
	}
	
	public ProgressLine(String label) {
		this(label, new LinkedList<>());
	}
	
	public ProgressLine(String label, List<String> items) {
		this(label, items, null);
	}
	
	public ProgressLine(String label, List<String> items, String active) {
		super();
		this.items = new LinkedList<>(items);
		this.active = active;
		
		this.buildUI(label);
	}
	
	private void buildUI(String label) {
		this.getContent().setSizeFull();
		this.getContent().getStyle().set("flex-direction", "column");
		this.getContent().getStyle().set("position", "relative");
		
		lblHeadline = new Span(label);
		lblHeadline.setWidth("100%");
		progressLayout = new FlexLayout();
		progressLayout.getStyle().set("flex-direction", "column");
		this.getContent().add(lblHeadline, progressLayout);
		
		this.buildProgressLine();
	}
	
	private void buildProgressLine() {
		progressLayout.removeAll();
		this.items.stream().forEach(item -> addProgressStep(item));

		setProgressStepStates();
		Div endline = new Div();
		endline.addClassName("end-line");
		Div endlineArrow = new Div();
		endlineArrow.addClassName("end-line-arrow");
		progressLayout.add(endline, endlineArrow);
	}

	private void setProgressStepStates() {
		if (StringUtils.isNotBlank(active)) {
			Iterator<ItemLabel> descendingIterator = itemLabels.descendingIterator();
			boolean activeFound = false;
			while (descendingIterator.hasNext()) {
				ItemLabel itemLabel = descendingIterator.next();
				if (activeFound) {
					itemLabel.relatedDiv.addClassName("done");
				}
				if (itemLabel.name.equalsIgnoreCase(active)) {
					itemLabel.relatedDiv.addClassName("active");
					activeFound = true;
				}
			}
		}
	}

	private void addProgressStep(String item) {
		Div itemLineBefore = new Div();
		itemLineBefore.addClassNames("item-line", "item-line-before");
		Span label = new Span(item);
		
		Div itemLabelDiv = new Div(label);
		itemLabelDiv.addClassName("item-label");
		ItemLabel itemLabel = new ItemLabel();
		itemLabel.name = item;
		itemLabels.add(itemLabel);
		
		Div itemLineAfter = new Div();
		itemLineAfter.addClassNames("item-line", "item-line-after");
		
		FlexLayout step = new FlexLayout(itemLineBefore, itemLabelDiv, itemLineAfter);
		itemLabel.relatedDiv = step;
		step.addClassName("progress-step");
		progressLayout.add(step);
	}
	
	class ItemLabel {
		private String name;
		private FlexLayout relatedDiv;
		
	}

	@Override
	public void setItems(Collection<String> items) {
		this.items = new LinkedList<>(items);
		this.buildProgressLine();
	}

	public void setActiveProgress(String active) {
		this.active = active;
		setProgressStepStates();
	}
}
