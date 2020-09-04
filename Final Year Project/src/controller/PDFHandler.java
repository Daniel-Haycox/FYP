package controller;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import javafx.collections.ObservableList;
import model.Customer;
import model.Owner;
import model.StockItem;

public class PDFHandler {
	
	private DateFormat dateformat;
	private DateFormat dateformat2;
	private Date date;
	private String dest;
	private File file;
	private PdfWriter writer;
	private PdfDocument pdfdoc;
	private Document doc;

	public PDFHandler() {
		
	}
	
	/**
	 * Generates the Invoice PDF
	 * @param o Owner Object
	 * @param c Customer Object
	 * @param items List of items the customer is buying
	 * @param invoicenumber the invoice number
	 * @throws Exception if there are any major errors with PDF syntax
	 */
	public void generate(Owner o, Customer c, ObservableList<StockItem> items, int invoicenumber) throws Exception {
		
		final DecimalFormat df2 = new DecimalFormat("#.##");
		df2.setMinimumFractionDigits(2);
		
		dateformat = new SimpleDateFormat("dd-MM-yyyy");
		dateformat2 = new SimpleDateFormat("dd/MM/yyyy");
		date = new Date();
		
		dest = "./Invoices/" + c.getName() + " " + dateformat.format(date) + ".pdf";
		file = new File(dest);
		file.getParentFile().mkdirs();
		
		writer = new PdfWriter(dest);
		pdfdoc = new PdfDocument(writer);
		doc = new Document(pdfdoc);
		
		Paragraph header1 = new Paragraph(o.getName());
		header1.setFontSize(24);
		header1.setBold();
		
		doc.add(header1);
		
		Table table1= new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
		table1.setBorder(Border.NO_BORDER);
		
		Cell table1left = new Cell();
		table1left.add(new Paragraph(o.getStreet() + "\n" + o.getCity() + "\n" + o.getPostcode() + "\n" + o.getPhone()));
		table1left.setFontSize(12);
		table1left.setBorder(Border.NO_BORDER);
		Cell table1right = new Cell();
		table1right.add(new Paragraph("Date: " + dateformat2.format(date) + "\nDue Date: " + dateformat2.format(dueDateCalc(Integer.parseInt(o.getDays()))) + "\n\nInvoice #: " + (invoicenumber + 1)));
		table1right.setTextAlignment(TextAlignment.RIGHT);
		table1right.setFontSize(12);
		table1right.setBorder(Border.NO_BORDER);
		
		table1.addCell(table1left);
		table1.addCell(table1right);
		
		doc.add(table1);
		
		Paragraph header2 = new Paragraph("\nBILLED TO:");
		header2.setFontSize(14);
		header2.setBold();
		header2.setMarginBottom(0);
		
		doc.add(header2);
		
		Paragraph customer = new Paragraph("    " +
				c.getName() + "\n    " +
				c.getStreet() + "\n    " +
				c.getCity() + "\n    " +
				c.getPostcode());
		customer.setFontSize(12);
		customer.setMarginTop(0);
		customer.setMarginBottom(10);
		
		doc.add(customer);
		
		float[] columnwidths2 = {10,60,15,15};
		Table itemtable = new Table(UnitValue.createPercentArray(columnwidths2)).useAllAvailableWidth();
		itemtable.setBorder(Border.NO_BORDER);
		
		Cell itemheader1 = new Cell();
		itemheader1.add(new Paragraph("QTY"));
		itemheader1.setFontSize(12);
		itemheader1.setBold();
		itemheader1.setBorder(new SolidBorder(ColorConstants.BLACK, 1));
		itemheader1.setTextAlignment(TextAlignment.LEFT);
		itemheader1.setPaddingLeft(5);
		Cell itemheader2 = new Cell();
		itemheader2.add(new Paragraph("Description"));
		itemheader2.setFontSize(12);
		itemheader2.setBold();
		itemheader2.setBorder(new SolidBorder(ColorConstants.BLACK, 1));
		itemheader2.setTextAlignment(TextAlignment.LEFT);
		itemheader2.setPaddingLeft(5);
		Cell itemheader3 = new Cell();
		itemheader3.add(new Paragraph("Unit Price"));
		itemheader3.setFontSize(12);
		itemheader3.setBold();
		itemheader3.setBorder(new SolidBorder(ColorConstants.BLACK, 1));
		itemheader3.setTextAlignment(TextAlignment.RIGHT);
		itemheader3.setPaddingRight(5);
		Cell itemheader4 = new Cell();
		itemheader4.add(new Paragraph("Amount"));
		itemheader4.setFontSize(12);
		itemheader4.setBold();
		itemheader4.setBorder(new SolidBorder(ColorConstants.BLACK, 1));
		itemheader4.setTextAlignment(TextAlignment.RIGHT);
		itemheader4.setPaddingRight(5);
		
		itemtable.addCell(itemheader1);
		itemtable.addCell(itemheader2);
		itemtable.addCell(itemheader3);
		itemtable.addCell(itemheader4);

		double st = 0.00;
		
		for (StockItem i : items) {
			Cell quantcell = new Cell();
			quantcell.add(new Paragraph(Integer.toString(i.getQuantity())).setMargin(0).setTextAlignment(TextAlignment.LEFT));
			quantcell.setFontSize(11);
			quantcell.setBorder(new SolidBorder(ColorConstants.GRAY, 1));
			quantcell.setBorderRight(Border.NO_BORDER);
			quantcell.setBorderLeft(new SolidBorder(ColorConstants.BLACK, 1));
			quantcell.setPaddingLeft(5);
			Cell namecell = new Cell();
			namecell.add(new Paragraph(i.getName()).setMargin(0).setTextAlignment(TextAlignment.LEFT));
			namecell.setFontSize(11);
			namecell.setBorder(new SolidBorder(ColorConstants.GRAY, 1));
			namecell.setBorderLeft(Border.NO_BORDER);
			namecell.setBorderRight(Border.NO_BORDER);
			namecell.setPaddingLeft(5);
			Cell unitpricecell = new Cell();
			unitpricecell.add(new Paragraph(df2.format(i.getPrice())).setMargin(0).setTextAlignment(TextAlignment.RIGHT));
			unitpricecell.setFontSize(11);
			unitpricecell.setBorder(new SolidBorder(ColorConstants.GRAY, 1));
			unitpricecell.setBorderLeft(Border.NO_BORDER);
			unitpricecell.setBorderRight(Border.NO_BORDER);
			unitpricecell.setPaddingRight(5);
			Cell amountcell = new Cell();
			amountcell.add(new Paragraph(df2.format(i.getPrice() * i.getQuantity())).setMargin(0).setTextAlignment(TextAlignment.RIGHT));
			amountcell.setFontSize(11);
			amountcell.setBorder(new SolidBorder(ColorConstants.GRAY, 1));
			amountcell.setBorderLeft(Border.NO_BORDER);
			amountcell.setBorderRight(new SolidBorder(ColorConstants.BLACK, 1));
			amountcell.setPaddingRight(5);
			
			st += (i.getPrice() * i.getQuantity());
			
			if (items.indexOf(i) == items.size() - 1) {
				quantcell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
				namecell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
				unitpricecell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
				amountcell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
			}
			
			itemtable.addCell(quantcell);
			itemtable.addCell(namecell);
			itemtable.addCell(unitpricecell);
			itemtable.addCell(amountcell);
			
		}
		
		for (int x = 0; x < 2; x++) {
			Cell blank = new Cell();
			blank.setBorder(Border.NO_BORDER);
			itemtable.addCell(blank);
		}
		
		Cell subtotalheader = new Cell();
		subtotalheader.add(new Paragraph("Total:").setTextAlignment(TextAlignment.RIGHT));
		subtotalheader.setFontSize(11);
		subtotalheader.setBold();
		subtotalheader.setBorder(Border.NO_BORDER);
		subtotalheader.setPaddingRight(5);
		
		Cell subtotal = new Cell();
		subtotal.add(new Paragraph(df2.format(st)).setTextAlignment(TextAlignment.RIGHT));
		subtotal.setFontSize(11);
		subtotal.setBorder(Border.NO_BORDER);
		subtotal.setPaddingRight(5);
		
		itemtable.addCell(subtotalheader);
		itemtable.addCell(subtotal);
		
		doc.add(itemtable);
		
		doc.close();

	}
	
	/**
	 * calculates the date the invoice will be due using value provided from owner object
	 * @param daystilldue number of days until invoice is due
	 * @return date object which is the date the invoice is due
	 */
	public Date dueDateCalc(int daystilldue) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, daystilldue);
		return c.getTime();		
	}
	
}
