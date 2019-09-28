package com.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.demo.model.Education;
import com.demo.model.Experience;
import com.demo.model.Header;
import com.demo.model.Project;
import com.demo.model.Resume;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class PdfOneGenerator {

	private static final Logger log = Logger.getLogger(PdfOneGenerator.class);
	/* Fonts for lines */
	private static final Font nameStyle = new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD);
	private static final Font emptyLineStyle = new Font(Font.FontFamily.HELVETICA, 4f, Font.BOLD);
	private static final Font headingStyle = new Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD);
	private static final Font contactStyle = new Font(Font.FontFamily.HELVETICA, 11f, Font.ITALIC);
	private static final Font contentStyle = new Font(Font.FontFamily.HELVETICA, 10f);

	public String createDocument(Resume resume) throws IOException {
		Document document = new Document();
		try {
			String file_location = "src/main/file/" + resume.getHeader().getName() + ".pdf";
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(file_location)));
			log.info("==========Pdf document is opened============");
			document.open();
			if (resume != null) {
				if (resume.getHeader() != null) {
					addMetaData(document, resume.getHeader().getName());
					log.info("Adding meta data");
				}
				addLayoutConfig(document);
				log.info("Adding page layout configurations");
				addLines(writer);
				if (resume.getHeader() != null) {
					addHeader(document, resume.getHeader());
					log.info("Adding header details");
				}
				if (resume.getEducation() != null) {
					addEducation(document, resume.getEducation());
					log.info("Adding educational data");
				}
				if (resume.getExperience() != null) {
					addExperience(document, resume.getExperience());
					log.info("Adding professional experience");
				}
				if (resume.getSkills() != null) {
					addSkills(document, resume.getSkills());
					log.info("Adding skill section");
				}
				if (resume.getProjects() != null) {

					addProjects(document, resume.getProjects());
					log.info("Adding projects section");
				}
			}
			document.close();
			writer.close();
			log.info("==========Pdf created successfully============");

			return "Success";
		} catch (Exception e) {
			// TODO: handle exception
			log.error("==========Error while creating pdf============");
			log.error(e.getMessage());
		}

		return "Failed";
	}

	public byte[] getDocument(String file_name) {
		String file_location = "src/main/file/" + file_name + ".pdf";
		File file = new File(file_location);
		byte[] bytesArray = new byte[(int) file.length()];
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			fis.read(bytesArray);
			fis.close();
			return bytesArray;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static void addMetaData(Document document, String author) {
		document.addTitle("My Resume PDF");
		document.addSubject("Using iText");
		document.addKeywords("Java, PDF, iText");
		document.addAuthor(author);
		document.addCreator("BuildByResume.com");
	}

	public static void addLayoutConfig(Document document) {
		/* Margins */
		document.setPageSize(PageSize.A4);
		document.setMargins(50f, 44f, 69f, 69f);
	}

	public static void addEmptyLines(Document document) throws DocumentException {
		Paragraph emptyLine = new Paragraph("", emptyLineStyle);
		document.add(emptyLine);
	}

	public static void addHeader(Document document, Header header) {
		try {
			Paragraph title = new Paragraph(header.getName() + "\n", nameStyle);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);

			addEmptyLines(document);

			PdfPTable table = new PdfPTable(new float[] { 20f, 40f, 40f });
			table.setPaddingTop(30f);
			table.getDefaultCell().setBorder(0);
			table.setWidthPercentage(100f);

			PdfPCell addressCell = new PdfPCell(new Phrase(header.getAddress(), contentStyle));
			addressCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			addressCell.setBorder(Rectangle.NO_BORDER);
			table.addCell(addressCell);

			PdfPCell personalWebsite = new PdfPCell(new Phrase(header.getWebsite(), contentStyle));
			personalWebsite.setHorizontalAlignment(Element.ALIGN_CENTER);
			personalWebsite.setBorder(Rectangle.NO_BORDER);
			table.addCell(personalWebsite);

			PdfPCell emailCell = new PdfPCell(new Phrase(header.getEmailAddress(), contentStyle));
			emailCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			emailCell.setBorder(Rectangle.NO_BORDER);
			table.addCell(emailCell);

			PdfPCell phoneCell = new PdfPCell(new Phrase(header.getPhoneNumber(), contentStyle));
			phoneCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			phoneCell.setBorder(Rectangle.NO_BORDER);
			table.addCell(phoneCell);

			PdfPCell githubCell = new PdfPCell(new Phrase(header.getGithub(), contentStyle));
			githubCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			githubCell.setBorder(Rectangle.NO_BORDER);
			table.addCell(githubCell);

			PdfPCell linkedInCell = new PdfPCell(new Phrase(header.getLinkedin(), contentStyle));
			linkedInCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			linkedInCell.setBorder(Rectangle.NO_BORDER);
			table.addCell(linkedInCell);

			document.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void addLines(PdfWriter writer) {

		/*
		 * PdfContentByte canvas = writer.getDirectContent();
		 * canvas.setColorStroke(BaseColor.BLACK ); canvas.moveTo(35, 735);
		 * canvas.lineTo(560, 735); canvas.closePathStroke();
		 *
		 * canvas.setColorStroke(BaseColor.BLACK ); canvas.moveTo(35, 610);
		 * canvas.lineTo(560, 610); canvas.closePathStroke();
		 */
	}

	public static void addEducation(Document document, ArrayList<Education> education) throws DocumentException {
		PdfPTable tablehead = new PdfPTable(new float[] { 100f });
		tablehead.getDefaultCell().setBorder(0);
		tablehead.setWidthPercentage(100f);

		PdfPCell headCell = new PdfPCell();
		Paragraph title = new Paragraph("Education", nameStyle);
		headCell.addElement(title);
		headCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headCell.setBorder(Rectangle.BOTTOM);
		headCell.setBorderWidth(1f);
		tablehead.addCell(headCell);

		document.add(tablehead);
		addEmptyLines(document);

		PdfPTable table = new PdfPTable(new float[] { 70f, 30f });
		table.getDefaultCell().setBorder(0);
		table.setWidthPercentage(100f);

		for (Education edu : education) {
			PdfPCell collegeCell = new PdfPCell();
			Paragraph colName = new Paragraph(edu.getDegree() + " | " + edu.getName(), headingStyle);
			Paragraph colPlace = new Paragraph(edu.getLocation(), contactStyle);
			Paragraph colCourse = new Paragraph(edu.getMajors(), contactStyle);
			collegeCell.addElement(colName);
			collegeCell.addElement(colPlace);
			collegeCell.addElement(colCourse);
			collegeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			collegeCell.setBorder(Rectangle.NO_BORDER);
			table.addCell(collegeCell);

			PdfPCell collegeTimeCell = new PdfPCell();
			Paragraph colTime = new Paragraph(edu.getPeriod(), headingStyle);
			colTime.setAlignment(Element.ALIGN_RIGHT);
			Paragraph colGPA = new Paragraph("GPA: " + edu.getGpa(), contactStyle);
			colGPA.setAlignment(Element.ALIGN_RIGHT);
			collegeTimeCell.addElement(colTime);
			collegeTimeCell.addElement(colGPA);
			collegeTimeCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			collegeTimeCell.setBorder(Rectangle.NO_BORDER);
			table.addCell(collegeTimeCell);
		}
		document.add(table);
		addEmptyLines(document);

	}

	public static void addExperience(Document document, ArrayList<Experience> experiences) throws DocumentException {
		PdfPTable tablehead = new PdfPTable(new float[] { 100f });
		tablehead.getDefaultCell().setBorder(0);
		tablehead.setWidthPercentage(100f);

		PdfPCell headCell = new PdfPCell();
		Paragraph title = new Paragraph("Experiences", nameStyle);
		headCell.addElement(title);
		headCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headCell.setBorder(Rectangle.BOTTOM);
		headCell.setBorderWidth(1f);
		tablehead.addCell(headCell);

		document.add(tablehead);
		addEmptyLines(document);
		for (Experience exp : experiences) {
			/* header */
			PdfPTable table = new PdfPTable(new float[] { 70f, 30f });
			table.getDefaultCell().setBorder(0);
			table.setWidthPercentage(100f);

			PdfPCell jobCell = new PdfPCell();
			Paragraph jobName = new Paragraph(exp.getCompany() + " " + exp.getLocation(), headingStyle);
			Paragraph jobRole = new Paragraph(exp.getJobrole(), contactStyle);
			jobCell.addElement(jobName);
			jobCell.addElement(jobRole);
			jobCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			jobCell.setBorder(Rectangle.NO_BORDER);
			table.addCell(jobCell);

			PdfPCell jobTimeCell = new PdfPCell();
			Paragraph jobTime = new Paragraph(exp.getPeriod(), headingStyle);
			jobTime.setAlignment(Element.ALIGN_RIGHT);
			jobTimeCell.addElement(jobTime);
			jobTimeCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			jobTimeCell.setBorder(Rectangle.NO_BORDER);
			table.addCell(jobTimeCell);

			document.add(table);

			/* responsibilities */
			PdfPTable tableresp = new PdfPTable(new float[] { 100f });
			tableresp.getDefaultCell().setBorder(0);
			tableresp.setWidthPercentage(100f);

			PdfPCell resCell = new PdfPCell();
			List list = new List(List.UNORDERED);
			for (String res : exp.getResponsibilites()) {
				ListItem item = new ListItem(res, contentStyle);
				item.setAlignment(Element.ALIGN_JUSTIFIED);
				list.add(item);
			}
			resCell.addElement(list);
			// resCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			resCell.setBorder(Rectangle.NO_BORDER);
			tableresp.addCell(resCell);
			document.add(tableresp);

		}
	}

	public static void addSkills(Document document, Map<String, String> skill) throws DocumentException {
		PdfPTable tablehead = new PdfPTable(new float[] { 100f });
		tablehead.getDefaultCell().setBorder(0);
		tablehead.setWidthPercentage(100f);

		PdfPCell headCell = new PdfPCell();
		Paragraph title = new Paragraph("Skills", nameStyle);
		headCell.addElement(title);
		headCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headCell.setBorder(Rectangle.BOTTOM);
		headCell.setBorderWidth(1f);
		tablehead.addCell(headCell);

		document.add(tablehead);
		addEmptyLines(document);

		PdfPTable table = new PdfPTable(new float[] { 40f, 60f });
		table.getDefaultCell().setBorder(0);
		table.setWidthPercentage(100f);

		for (Entry<String, String> me : skill.entrySet()) {
			PdfPCell category = new PdfPCell();
			Paragraph categoryName = new Paragraph(me.getKey(), contentStyle);
			category.addElement(categoryName);
			category.setHorizontalAlignment(Element.ALIGN_LEFT);
			category.setBorder(Rectangle.NO_BORDER);
			table.addCell(category);

			PdfPCell categoryValue = new PdfPCell();
			Paragraph categoryVal = new Paragraph(me.getValue(), contentStyle);
			categoryValue.addElement(categoryVal);
			categoryValue.setHorizontalAlignment(Element.ALIGN_LEFT);
			categoryValue.setBorder(Rectangle.NO_BORDER);
			table.addCell(categoryValue);
		}
		document.add(table);

	}

	public static void addProjects(Document document, ArrayList<Project> projectList) throws DocumentException {
		PdfPTable tablehead = new PdfPTable(new float[] { 100f });
		tablehead.getDefaultCell().setBorder(0);
		tablehead.setWidthPercentage(100f);

		PdfPCell headCell = new PdfPCell();
		Paragraph title = new Paragraph("Projects", nameStyle);
		headCell.addElement(title);
		headCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headCell.setBorder(Rectangle.BOTTOM);
		headCell.setBorderWidth(1f);
		tablehead.addCell(headCell);

		document.add(tablehead);
		addEmptyLines(document);
		for (Project pro : projectList) {
			/* header */
			PdfPTable table = new PdfPTable(new float[] { 100f });
			table.getDefaultCell().setBorder(0);
			table.setWidthPercentage(100f);

			PdfPCell projectNameCell = new PdfPCell();
			Paragraph proName = new Paragraph(pro.getName(), headingStyle);
			projectNameCell.addElement(proName);
			projectNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			projectNameCell.setBorder(Rectangle.NO_BORDER);
			table.addCell(projectNameCell);

			PdfPCell descCell = new PdfPCell();
			Paragraph proDesc = new Paragraph(pro.getDescription(), contentStyle);
			Paragraph proTech = new Paragraph("Technologies: " + pro.getTechnology(), contentStyle);
			descCell.addElement(proDesc);
			descCell.addElement(proTech);
			descCell.setBorder(Rectangle.NO_BORDER);
			table.addCell(descCell);

			document.add(table);
		}
	}
}