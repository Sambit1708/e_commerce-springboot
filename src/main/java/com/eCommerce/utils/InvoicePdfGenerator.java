package com.eCommerce.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.eCommerce.invoice.Invoice;
import com.eCommerce.invoice.InvoiceItems;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

@Component
public class InvoicePdfGenerator {

	public File generatePDF(Invoice invoice) {
		String imagePath = "src/main/resources/static/eCommerce.jpg";
		String path = "invoice.pdf";
		File file = new File(path);
		try {
			
			// To add background image
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			PdfWriter pdfWriter = new PdfWriter(fileOutputStream);
			PdfDocument pdfDocument = new PdfDocument(pdfWriter);
			pdfDocument.setDefaultPageSize(PageSize.A4);
			Document document = new Document(pdfDocument);
			
			ImageData bgLogoData = ImageDataFactory.create(imagePath);
	        Image bgLogo = new Image(bgLogoData);
	        
	        bgLogo.setFixedPosition(200, 270);
	        bgLogo.setOpacity(0.1f);
	        bgLogo.setWidth(100f);
	        document.add(bgLogo);
			
	        // Set width of type
			float threeColWidth = 190f;
			float twoCol = 285f;
			float twoCol150 = twoCol + 150f;
			float twoColumnWidth[] = { twoCol150, twoCol };
			float fourColTableWidth[] = { 250f, 100f, 110f, 110f };
			float fullWidth[] = { threeColWidth * 3 };
			
			
			Table table = new Table(twoColumnWidth);
			table.addCell(new Cell().add(new Paragraph("Invoice"))
									.setBorder(Border.NO_BORDER).setBold());
			
			Table nestedTable = new Table(new float[] {twoCol/2, twoCol/2});
			nestedTable.addCell(getHeaderTextCell("Invoice No:"));
			nestedTable.addCell(getHeaderTextCellValue("# "+String.valueOf(invoice.getInvoiceNumber())));
			nestedTable.addCell(getHeaderTextCell("Invoice Date:"));
			nestedTable.addCell(getHeaderTextCellValue(invoice.getInvoiceDate()));
			
			table.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));
			
			Border gb = new SolidBorder(new DeviceGray(0.5f), 2f);
			Table divider = new Table(fullWidth);
			divider.setBorder(gb);
			
			
			document.add(table);
			oneSpaceFromAbove(document);
			document.add(divider);
			oneSpaceFromAbove(document);
			
			Table twoColTable = new Table(twoColumnWidth);
			twoColTable.addCell(getBillingAndShippingCell("Billing Information"));
			twoColTable.addCell(getBillingAndShippingCell("Shipping Information"));
			document.add(twoColTable.setMarginBottom(12f));
			
			Table twoColTable2 = new Table(twoColumnWidth);
			twoColTable2.addCell(getCell10fLeft("Company", true));
			twoColTable2.addCell(getCell10fLeft("Shipping To", true));
			twoColTable2.addCell(getCell10fLeft("ECommerce-App", false));
			twoColTable2.addCell(getCell10fLeft(invoice.getCustomerName(), false));
			document.add(twoColTable2);
			
			Table twoColTable3 = new Table(twoColumnWidth);
			twoColTable3.addCell(getCell10fLeft("Shipping By", true));
			twoColTable3.addCell(getCell10fLeft("Shipping Address", true));
			twoColTable3.addCell(getCell10fLeft("ECommerce-App", false));
			twoColTable3.addCell(getCell10fLeft(invoice.getAddress().getAddress(), false));
			document.add(twoColTable3);
			
			float oneColumnWidth[] = { twoCol150 };
			
			Table oneColTable1 = new Table(oneColumnWidth);
			oneColTable1.addCell(getCell10fLeft("Shipping From", true));
			oneColTable1.addCell(
					getCell10fLeft("76, 27th Main Rd, near IQRA School,\n "
								 + "Old Madiwala, Jay Bheema Nagar,\n 1st Stage, "
								 + "BTM 1st Stage", false));
			oneColTable1.addCell(getCell10fLeft("Email", true));
			oneColTable1.addCell(getCell10fLeft("sambitkhandai6@gmail.com", false));
			document.add(oneColTable1.setMarginBottom(10f));
			
			Border gb1 = new DashedBorder(new DeviceGray(0.5f), 1f);
			Table divider1 = new Table(fullWidth);
			document.add(divider1.setBorder(gb1));
			oneSpaceFromAbove(document);
			
			Paragraph productParagraph = new Paragraph("Product");
			document.add(productParagraph.setBold());
			
			Table fourColTableHeader = new Table(fourColTableWidth);
			addInvoiceItemHeaderToTable(document, fourColTableHeader);
			
			Table fourColTableData = new Table(fourColTableWidth);
			addInvoiceItemDataToTable(document, fourColTableData, fourColTableWidth, invoice.getInvoiceItems());
			
			
			
			document.add(getDashedTable().setPaddingRight(17f));
			oneSpaceFromAbove(document);
			document.add(divider.setBorder(gb));
			oneSpaceFromAbove(document);
			
			Table tb = new Table(fullWidth);
			tb.addCell(
					new Cell().add(new Paragraph("TERMS AND CONDITIONS\n"))
					.setBold().setBorder(Border.NO_BORDER));
			List<String> terms = new ArrayList<>();
			terms.add("1. The Seller shall not be liable to the Buyer directly or indirectly"
					+ "for any loss or damage suffered by the Buyer.");
			terms.add("2. The seller warrents the product for "
					+ "one (1) year from the date of shiptment");
			
			for(String tnc : terms) {
				tb.addCell(new Cell().add(new Paragraph(tnc)).setBorder(Border.NO_BORDER));
			}
			document.add(tb);
			document.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return file;
	}
	
	static Cell getHeaderTextCell(String textValue)
	{
		return new Cell().add(new Paragraph(textValue))
							.setBold().setBorder(Border.NO_BORDER)
							.setTextAlignment(TextAlignment.RIGHT);
	}
	
	static Cell getHeaderTextCellValue(String textValue) throws IOException
	{
		return new Cell().add(new Paragraph(textValue))
							.setBorder(Border.NO_BORDER)
							.setTextAlignment(TextAlignment.LEFT);
	}
	
	static Cell getBillingAndShippingCell(String textValue) throws IOException
	{
		return new Cell().add(new Paragraph(textValue))
							.setFontSize(12f).setBold()
							.setBorder(Border.NO_BORDER)
							.setTextAlignment(TextAlignment.LEFT);
	}
	
	static Cell getCell10fLeft(String textValue, boolean isBold) throws IOException
	{
		Cell myCell = new Cell().add(new Paragraph(textValue)).setFontSize(10f)
								.setBorder(Border.NO_BORDER)
								.setTextAlignment(TextAlignment.LEFT);
		return isBold ? myCell.setBold() : myCell;
	}
	
	static void oneSpaceFromAbove(Document document)
	{
		Paragraph oneSpaceFromAbove = new Paragraph("\n");
		document.add(oneSpaceFromAbove);
	}
	
	static Table getDashedTable() {
		Border dashedBorder = new DashedBorder(new DeviceGray(0.5f), 1f);
		Table divider = new Table(570);
		divider.setBorder(dashedBorder);
		return divider;
	}
	
	static void addInvoiceItemHeaderToTable(Document document, Table table) 
	{
		table.setBackgroundColor(new DeviceRgb(0, 0, 0), 435);
		
		table.addCell(new Cell().add(new Paragraph("Description")).setBold()
				.setFontColor(new DeviceRgb(255, 255, 255)).setBorder(Border.NO_BORDER));
		
		table.addCell(new Cell().add(new Paragraph("Quantity")).setBold()
				.setFontColor(new DeviceRgb(255, 255, 255))
				.setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
		
		table.addCell(new Cell().add(new Paragraph("Unit Price")).setBold()
				.setFontColor(new DeviceRgb(255, 255, 255))
				.setTextAlignment(TextAlignment.CENTER)
				.setBorder(Border.NO_BORDER)).setMarginRight(15f);

		table.addCell(new Cell().add(new Paragraph("Price")).setBold()
				.setFontColor(new DeviceRgb(255, 255, 255))
				.setTextAlignment(TextAlignment.RIGHT)
				.setBorder(Border.NO_BORDER)).setMarginRight(15f);
		
		document.add(table);
	}
	
	static void addInvoiceItemDataToTable(Document document, Table table, float[] fourColTableWidth, List<InvoiceItems> invoiceItems)
	{
		double totalProdSum = 0;
		for(InvoiceItems invoiceItem : invoiceItems) {
			double prodSum = invoiceItem.getQuantity()*invoiceItem.getUnitPrice();
			totalProdSum += prodSum;
			
			table.addCell(
					new Cell().add(new Paragraph(invoiceItem.getDescription()))
					.setBorder(Border.NO_BORDER)
			);
			
			table.addCell(
					new Cell().add(new Paragraph(String.valueOf(invoiceItem.getQuantity())))
					.setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER)
			);
			
			table.addCell(
					new Cell().add(new Paragraph(String.valueOf(invoiceItem.getUnitPrice())))
					.setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER)
			);
			
			table.addCell(
					new Cell().add(new Paragraph(String.valueOf(prodSum))).setPaddingRight(17f)
					.setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)
			);
		}
		document.add(table.setMarginBottom(20f));
		
		float oneTwo[] = { 1 + 125f, 190f * 2 };
		Table fourColTable3 = new Table(oneTwo);
		fourColTable3.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
		fourColTable3.addCell(new Cell().add(getDashedTable()).setBorder(Border.NO_BORDER).setPaddingRight(17f));
		document.add(fourColTable3);
		
		
		Table deliveryTable = new Table(fourColTableWidth);
		deliveryTable.addCell(
				new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)
				.setMarginLeft(10f)
		);
		deliveryTable.addCell(
				new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)
		);
		deliveryTable.addCell(
				new Cell().add(new Paragraph("Delivery Charge")).setBorder(Border.NO_BORDER)
				.setTextAlignment(TextAlignment.CENTER)
		);
		deliveryTable.addCell(
				new Cell().add(new Paragraph("50")).setPaddingRight(17f)
				.setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)
		);
		
		document.add(deliveryTable);
		
		Table fourColTable4 = new Table(fourColTableWidth);
		fourColTable4.addCell(
				new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)
				.setMarginLeft(10f)
		);
		fourColTable4.addCell(
				new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)
		);
		fourColTable4.addCell(
				new Cell().add(new Paragraph("Total")).setBorder(Border.NO_BORDER)
				.setTextAlignment(TextAlignment.CENTER)
		);
		fourColTable4.addCell(
				new Cell().add(new Paragraph(String.valueOf(totalProdSum))).setPaddingRight(17f)
				.setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)
		);
		
		document.add(fourColTable4);
	}
}
