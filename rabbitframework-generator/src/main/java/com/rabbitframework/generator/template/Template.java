package com.rabbitframework.generator.template;

import com.rabbitframework.commons.org.springframework.io.Resource;
import com.rabbitframework.commons.utils.ResourceUtils;
import com.rabbitframework.commons.utils.StringUtils;
import com.rabbitframework.generator.exceptions.GeneratorException;

import com.rabbitframework.generator.utils.Constants;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * @author: justin.liang
 * @date: 16/4/30 下午11:26
 */
public class Template {
    private static final Logger logger = LoggerFactory.getLogger(Template.class);
    public Map<String, JavaModeGenerate> templateMapping;
    private Configuration configuration;
    private StringTemplateLoader templateLoader;

    public Template() {
        configuration = new Configuration(Configuration.VERSION_2_3_23);
        // configuration.setObjectWrapper(new
        // DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        templateMapping = new HashMap<String, JavaModeGenerate>();
        templateLoader = new StringTemplateLoader();
        configuration.setTemplateLoader(templateLoader);
    }

    public void put(JavaModeGenerate javaModeGenerate) {
        try {
            Resource resource = ResourceUtils.getResource(javaModeGenerate.getTemplatePath());
            String fileName = resource.getFilename();
            String value = IOUtils.toString(resource.getInputStream());
            templateLoader.putTemplate(fileName, value);
            templateMapping.put(fileName, javaModeGenerate);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new GeneratorException(e);
        }
    }

    public Map<String, JavaModeGenerate> getTemplateMapping() {
        return Collections.unmodifiableMap(templateMapping);
    }

    public void printToConsole(Map<String, Object> params, String fileName) throws Exception {
        freemarker.template.Template template = configuration.getTemplate(fileName);
        template.process(params, new OutputStreamWriter(System.out));
    }

    public void printToFile(Map<String, Object> params, String tempFileName, String outPath, String outfileName) {
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        try {
            freemarker.template.Template template = configuration.getTemplate(tempFileName);
            String packageName = (String) params.get(Constants.PACKAGE_NAME_KEY);
            File directory = getDirectory(outPath, packageName);
            File targetFile = new File(directory, outfileName);
            fos = new FileOutputStream(targetFile, false);
            osw = new OutputStreamWriter(fos, Constants.ENCODING);
            template.process(params, osw);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new GeneratorException(e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (osw != null) {
                    osw.close();
                }
            } catch (Exception e) {

            }
        }
    }

//    private void writeFile(File file, String content, String fileEncoding) throws IOException {
//        FileOutputStream fos = new FileOutputStream(file, false);
//        OutputStreamWriter osw;
//        if (fileEncoding == null) {
//            osw = new OutputStreamWriter(fos);
//        } else {
//            osw = new OutputStreamWriter(fos, fileEncoding);
//        }
//
//        BufferedWriter bw = new BufferedWriter(osw);
//        bw.write(content);
//        bw.close();
//    }


    public File getDirectory(String targetProject, String targetPackage) {
        File project = new File(targetProject);
        if (!project.isDirectory()) {
            //throw new GeneratorException("The specified target project directory " + targetProject + " does not exist");
            project.mkdirs();
        }

        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(targetPackage, ".");
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
            sb.append(File.separatorChar);
        }

        File directory = new File(project, sb.toString());
        if (!directory.isDirectory()) {
            boolean rc = directory.mkdirs();
            if (!rc) {
                throw new GeneratorException("Cannot create directory " + directory.getAbsolutePath());
            }
        }

        return directory;
    }
}
