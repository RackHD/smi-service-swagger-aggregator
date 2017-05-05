/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.service.smi.swagger.aggregator.service;

import static org.asciidoctor.AttributesBuilder.attributes;
import static org.asciidoctor.OptionsBuilder.options;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Attributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zeroturnaround.zip.ZipUtil;

import com.dell.isg.smi.service.smi.swagger.aggregator.SMISwaggerResourceProvider;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import springfox.documentation.swagger.web.SwaggerResource;

@Component
@EnableScheduling
public class GenerateSwaggerPdfBean {

	@Autowired
	SMISwaggerResourceProvider swaggerResourceProvider;

	@Value("${asciidoctor.generated}")
	private String generation_path;

	@Value("${asciidoctor.asciiExt}")
	private String asciiExt;

	@Value("${asciidoctor.docType}")
	private String docType;
	
	@Value("${asciidoctor.reports}")
	private String report_path;
	
	@Value("${asciidoctor.allDocs}")
	private String zipFile;

	@Async
	@Scheduled(fixedDelayString = "${asciidoctor.schedule.interval}", initialDelayString = "${asciidoctor.schedule.initial}")
	public void startSwaggerpdfGeneration() {
		System.out.println("Pdf generation task --> Start ");
		boolean isDocumentGenerated = false;
		List<SwaggerResource> swaggerResources = swaggerResourceProvider.get();
		for (SwaggerResource resorce : swaggerResources) {
			String apiDocumentName = resorce.getName() + "-" + resorce.getSwaggerVersion();
			Path generationPath = FileSystems.getDefault().getPath(generation_path);
			Path reportPath = FileSystems.getDefault().getPath(report_path);
			Path pdfFilePath = FileSystems.getDefault().getPath(report_path, apiDocumentName + "." + docType);
			Path asccidocFilePath = FileSystems.getDefault().getPath(generation_path, apiDocumentName);
			Path asccidocFileExtentionPath = FileSystems.getDefault().getPath(generation_path,
					apiDocumentName + "." + asciiExt);
			if (Files.notExists(generationPath)) generationPath.toFile().mkdirs();
			if (Files.notExists(reportPath)) reportPath.toFile().mkdirs();
			if (Files.notExists(pdfFilePath)) {
				try {
					Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
							.withMarkupLanguage(MarkupLanguage.ASCIIDOC).withOutputLanguage(Language.EN).withGeneratedExamples().withSwaggerMarkupLanguage(MarkupLanguage.ASCIIDOC)
							.withPathsGroupedBy(GroupBy.TAGS).withInterDocumentCrossReferences().build();
					Swagger2MarkupConverter.from(new URL(resorce.getLocation().trim())).withConfig(config).build()
							.toFile(asccidocFilePath);
					Attributes attributes = attributes().sectionNumbers(true).tableOfContents(true).showTitle(true).get();
					Map<String, Object> options = options().attributes(attributes).inPlace(true).toFile(pdfFilePath.toFile()).backend(docType).asMap();

					Asciidoctor asciidoctor = Asciidoctor.Factory.create();
					asciidoctor.convertFile(asccidocFileExtentionPath.toFile(), options);
					asciidoctor.shutdown();
					FileUtils.cleanDirectory(generationPath.toFile());
					isDocumentGenerated = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (isDocumentGenerated) compressDocuments();
		System.out.println("Pdf generation task --> Finished.");
	}
	
	public void compressDocuments(){
		try {
			Path reportPath = FileSystems.getDefault().getPath(report_path);
			Path tempZipPath = FileSystems.getDefault().getPath(generation_path, zipFile);
			Path zipFilePath = FileSystems.getDefault().getPath(report_path, zipFile);
			Files.deleteIfExists(tempZipPath);
			Files.deleteIfExists(zipFilePath);
			ZipUtil.pack(reportPath.toFile(), tempZipPath.toFile());
			Files.copy(tempZipPath, zipFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
