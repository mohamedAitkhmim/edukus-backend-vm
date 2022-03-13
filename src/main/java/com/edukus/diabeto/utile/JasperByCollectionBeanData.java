package com.edukus.diabeto.utile;

import com.edukus.diabeto.persistence.entity.MeasureEntity;
import com.edukus.diabeto.persistence.entity.MeasureTypeEntity;
import com.edukus.diabeto.persistence.repository.MeasureRepository;
import com.edukus.diabeto.persistence.repository.MeasureTypeRepository;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class JasperByCollectionBeanData {

  @Value("${edukus.report.inputfile}")
  private String inputFile;
  @Value("${edukus.report.outputfile}")
  private String outputPath;

  @Autowired
  MeasureRepository measureRepository;
  @Autowired
  MeasureTypeRepository measureTypeRepository;

  public byte[] generateReport(String username, String type, LocalDateTime startDate, LocalDateTime endDate) throws JRException, IOException {

    Logger LOGGER = Logger.getLogger(JasperByCollectionBeanData.class.getName());

    LOGGER.info("generateReport class");

    List<MeasureEntity> measureEntityList = measureRepository.findByMeasureTypeAndDate(username, type, startDate, endDate.plusDays(1));
    if (measureEntityList.isEmpty()) {
      throw new RuntimeException("cannot generate report measures not found");
    }

    MeasureTypeEntity measureTypeEntity = measureTypeRepository.findByCode(type).orElseThrow(() -> new RuntimeException("measure type not found"));

    int night = 6;
    int morning = 12;
    int afternoon = 17;
    int evening = 22;

    int preMorning = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() < measureTypeEntity.getMinRefPrePrandial() && measure.getPrandial().equals(MeasureTime.PREPRANDIAL.getValue()) && measure.getDateTime().getHour() >= night
            && measure.getDateTime().getHour() < morning).count();
    int postMorning = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() < measureTypeEntity.getMinRefPrePrandial() && measure.getPrandial().equals(MeasureTime.POSTPRANDIAL.getValue()) && measure.getDateTime().getHour() >= night
            && measure.getDateTime().getHour() < morning).count();

    int preAfternoon = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() < measureTypeEntity.getMinRefPrePrandial() && measure.getPrandial().equals(MeasureTime.PREPRANDIAL.getValue()) && measure.getDateTime().getHour() >= morning
            && measure.getDateTime().getHour() < afternoon).count();
    int postAfternoon = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() < measureTypeEntity.getMinRefPrePrandial() && measure.getPrandial().equals(MeasureTime.POSTPRANDIAL.getValue()) && measure.getDateTime().getHour() >= morning
            && measure.getDateTime().getHour() < afternoon).count();

    int preEvening = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() < measureTypeEntity.getMinRefPrePrandial() && measure.getPrandial().equals(MeasureTime.PREPRANDIAL.getValue()) && measure.getDateTime().getHour() >= afternoon
            && measure.getDateTime().getHour() < evening).count();
    int postEvening = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() < measureTypeEntity.getMinRefPrePrandial() && measure.getPrandial().equals(MeasureTime.POSTPRANDIAL.getValue()) && measure.getDateTime().getHour() >= afternoon
            && measure.getDateTime().getHour() < evening).count();

    int preNight = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() < measureTypeEntity.getMinRefPrePrandial() && measure.getPrandial().equals(MeasureTime.PREPRANDIAL.getValue()) && (measure.getDateTime().getHour() >= evening
            || measure.getDateTime().getHour() < night)).count();
    int postNight = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() < measureTypeEntity.getMinRefPrePrandial() && measure.getPrandial().equals(MeasureTime.POSTPRANDIAL.getValue()) && (measure.getDateTime().getHour() >= evening
            || measure.getDateTime().getHour() < night)).count();

    MeasureReport measureReport1 = new MeasureReport("< " + measureTypeEntity.getMinRefPrePrandial(), preMorning, postMorning, preAfternoon, postAfternoon, preEvening, postEvening, preNight, postNight);

    preMorning = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMinRefPrePrandial() && measure.getValue() < measureTypeEntity.getMaxRefPostPrandial() && measure.getPrandial()
            .equals(MeasureTime.PREPRANDIAL.getValue()) && measure.getDateTime().getHour() >= night && measure.getDateTime().getHour() < morning).count();
    postMorning = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMinRefPrePrandial() && measure.getValue() < measureTypeEntity.getMaxRefPostPrandial() && measure.getPrandial()
            .equals(MeasureTime.POSTPRANDIAL.getValue()) && measure.getDateTime().getHour() >= night && measure.getDateTime().getHour() < morning).count();

    preAfternoon = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMinRefPrePrandial() && measure.getValue() < measureTypeEntity.getMaxRefPostPrandial() && measure.getPrandial()
            .equals(MeasureTime.PREPRANDIAL.getValue()) && measure.getDateTime().getHour() >= morning && measure.getDateTime().getHour() < afternoon).count();
    postAfternoon = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMinRefPrePrandial() && measure.getValue() < measureTypeEntity.getMaxRefPostPrandial() && measure.getPrandial()
            .equals(MeasureTime.POSTPRANDIAL.getValue()) && measure.getDateTime().getHour() >= morning && measure.getDateTime().getHour() < afternoon).count();

    preEvening = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMinRefPrePrandial() && measure.getValue() < measureTypeEntity.getMaxRefPostPrandial() && measure.getPrandial()
            .equals(MeasureTime.PREPRANDIAL.getValue()) && measure.getDateTime().getHour() >= afternoon && measure.getDateTime().getHour() < evening).count();
    postEvening = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMinRefPrePrandial() && measure.getValue() < measureTypeEntity.getMaxRefPostPrandial() && measure.getPrandial()
            .equals(MeasureTime.POSTPRANDIAL.getValue()) && measure.getDateTime().getHour() >= afternoon && measure.getDateTime().getHour() < evening).count();

    preNight = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMinRefPrePrandial() && measure.getValue() < measureTypeEntity.getMaxRefPostPrandial() && measure.getPrandial()
            .equals(MeasureTime.PREPRANDIAL.getValue()) && (measure.getDateTime().getHour() >= evening || measure.getDateTime().getHour() < night)).count();
    postNight = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMinRefPrePrandial() && measure.getValue() < measureTypeEntity.getMaxRefPostPrandial() && measure.getPrandial()
            .equals(MeasureTime.POSTPRANDIAL.getValue()) && (measure.getDateTime().getHour() >= evening || measure.getDateTime().getHour() < night)).count();

    MeasureReport measureReport2 = new MeasureReport(measureTypeEntity.getMinRefPrePrandial() + "-" + measureTypeEntity.getMaxRefPostPrandial(), preMorning, postMorning, preAfternoon, postAfternoon, preEvening,
        postEvening, preNight, postNight);

    preMorning = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMaxRefPostPrandial() && measure.getValue() < measureTypeEntity.getMinRefPostPrandial() * 2 && measure.getPrandial()
            .equals(MeasureTime.PREPRANDIAL.getValue()) && measure.getDateTime().getHour() >= night && measure.getDateTime().getHour() < morning).count();
    postMorning = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMaxRefPostPrandial() && measure.getValue() < measureTypeEntity.getMinRefPostPrandial() * 2 && measure.getPrandial()
            .equals(MeasureTime.POSTPRANDIAL.getValue()) && measure.getDateTime().getHour() >= night && measure.getDateTime().getHour() < morning).count();

    preAfternoon = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMaxRefPostPrandial() && measure.getValue() < measureTypeEntity.getMinRefPostPrandial() * 2 && measure.getPrandial()
            .equals(MeasureTime.PREPRANDIAL.getValue()) && measure.getDateTime().getHour() >= morning && measure.getDateTime().getHour() < afternoon).count();
    postAfternoon = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMaxRefPostPrandial() && measure.getValue() < measureTypeEntity.getMinRefPostPrandial() * 2 && measure.getPrandial()
            .equals(MeasureTime.POSTPRANDIAL.getValue()) && measure.getDateTime().getHour() >= morning && measure.getDateTime().getHour() < afternoon).count();

    preEvening = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMaxRefPostPrandial() && measure.getValue() < measureTypeEntity.getMinRefPostPrandial() * 2 && measure.getPrandial()
            .equals(MeasureTime.PREPRANDIAL.getValue()) && measure.getDateTime().getHour() >= afternoon && measure.getDateTime().getHour() < evening).count();
    postEvening = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMaxRefPostPrandial() && measure.getValue() < measureTypeEntity.getMinRefPostPrandial() * 2 && measure.getPrandial()
            .equals(MeasureTime.POSTPRANDIAL.getValue()) && measure.getDateTime().getHour() >= afternoon && measure.getDateTime().getHour() < evening).count();

    preNight = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMaxRefPrePrandial() && measure.getValue() < measureTypeEntity.getMinRefPostPrandial() * 2 && measure.getPrandial()
            .equals(MeasureTime.PREPRANDIAL.getValue()) && (measure.getDateTime().getHour() >= evening || measure.getDateTime().getHour() < night)).count();
    postNight = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMaxRefPrePrandial() && measure.getValue() < measureTypeEntity.getMinRefPostPrandial() * 2 && measure.getPrandial()
            .equals(MeasureTime.POSTPRANDIAL.getValue()) && (measure.getDateTime().getHour() >= evening || measure.getDateTime().getHour() < night)).count();

    MeasureReport measureReport3 = new MeasureReport(measureTypeEntity.getMaxRefPostPrandial() + "-" + measureTypeEntity.getMaxRefPrePrandial() * 2, preMorning, postMorning, preAfternoon, postAfternoon,
        preEvening, postEvening, preNight, postNight);

    preMorning = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMinRefPostPrandial() * 2 && measure.getPrandial().equals(MeasureTime.PREPRANDIAL.getValue()) && measure.getDateTime().getHour() >= night
            && measure.getDateTime().getHour() < morning).count();
    postMorning = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMinRefPostPrandial() * 2 && measure.getPrandial().equals(MeasureTime.POSTPRANDIAL.getValue()) && measure.getDateTime().getHour() >= night
            && measure.getDateTime().getHour() < morning).count();

    preAfternoon = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMinRefPostPrandial() * 2 && measure.getPrandial().equals(MeasureTime.PREPRANDIAL.getValue()) && measure.getDateTime().getHour() >= morning
            && measure.getDateTime().getHour() < afternoon).count();
    postAfternoon = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMinRefPostPrandial() * 2 && measure.getPrandial().equals(MeasureTime.POSTPRANDIAL.getValue()) && measure.getDateTime().getHour() >= morning
            && measure.getDateTime().getHour() < afternoon).count();

    preEvening = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMinRefPostPrandial() * 2 && measure.getPrandial().equals(MeasureTime.PREPRANDIAL.getValue()) && measure.getDateTime().getHour() >= afternoon
            && measure.getDateTime().getHour() < evening).count();
    postEvening = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMinRefPostPrandial() * 2 && measure.getPrandial().equals(MeasureTime.POSTPRANDIAL.getValue()) && measure.getDateTime().getHour() >= afternoon
            && measure.getDateTime().getHour() < evening).count();

    preNight = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMinRefPostPrandial() * 2 && measure.getPrandial().equals(MeasureTime.PREPRANDIAL.getValue()) && (measure.getDateTime().getHour() >= evening
            || measure.getDateTime().getHour() < night)).count();
    postNight = (int) measureEntityList.stream().filter(
        measure -> measure.getValue() >= measureTypeEntity.getMinRefPostPrandial() * 2 && measure.getPrandial().equals(MeasureTime.POSTPRANDIAL.getValue()) && (measure.getDateTime().getHour() >= evening
            || measure.getDateTime().getHour() < night)).count();

    MeasureReport measureReport4 = new MeasureReport("> " + measureTypeEntity.getMaxRefPrePrandial() * 2, preMorning, postMorning, preAfternoon, postAfternoon, preEvening, postEvening, preNight, postNight);


    /* Convert List to JRBeanCollectionDataSource */
    LOGGER.info("Convert List to JRBeanCollectionDataSource");
    JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(Arrays.asList(measureReport1, measureReport2, measureReport3, measureReport4));

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String dateRange = startDate.format(formatter) + " / " + endDate.format(formatter);

    /* Map to hold Jasper report Parameters */
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("CollectionBeanParam", itemsJRBean);
    parameters.put("DateRange", dateRange);
    //read jrxml file and creating jasperdesign object
    LOGGER.info("inputFile");
    InputStream input = new ClassPathResource(inputFile).getInputStream();
    LOGGER.info(new ClassPathResource(inputFile).getURI().toString());

    JasperDesign jasperDesign = JRXmlLoader.load(input);

    /*compiling jrxml with help of JasperReport class*/
    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

    /* Using jasperReport object to generate PDF */
    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

    /* Create folder if not exist */
    Files.createDirectories(Paths.get(outputPath));

    String pdfPath = generatedPdfPath(username, type, outputPath);

    /* outputStream to create PDF */
    OutputStream outputStream = new FileOutputStream(pdfPath);
    /* Write content to PDF file */
    JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

    return Files.readAllBytes(Paths.get(pdfPath));

  }

  public static String generatedPdfPath(String username, String type, String outputPath){
    return outputPath + username + "_" + type + ".pdf";
  }

}


