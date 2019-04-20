package gt.edu.umg.ingenieria.sistemas.laboratorio1.service;

import gt.edu.umg.ingenieria.sistemas.laboratorio1.model.Client;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 *
 * @author Josué Barillas (jbarillas)
 */
@Service
public class ReportService {
    public  String generaRepo(List<Client> clientList, ServletContext servletContext){
        String serverDir = servletContext.getRealPath("/");
        String directorio = "var/wwww";

        File reporteDir = new File(serverDir + directorio);
        List<File> filesInReportsDirectory = new ArrayList<>();
        if (!reporteDir.exists()){
            boolean directoryCreated = reporteDir.mkdirs();
        } else {
            try (Stream<Path> paths = Files.walk(Paths.get(serverDir + directorio))) {
                filesInReportsDirectory = paths
                        .filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String NombreArchivo = "/Clientes_1.html";
        if (!filesInReportsDirectory.isEmpty()){
            NombreArchivo = "/Clientes_" + (filesInReportsDirectory.size() + 1) + ".html";
        }
        NombreArchivo = directorio + NombreArchivo;
        String filePath = serverDir + NombreArchivo;
        NombreArchivo = "/" + NombreArchivo;

        File Archivo = new File(filePath);
        if (!Archivo.exists()) {
            try {
                boolean fileCreated = Archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table id = \"clientReport\" border = \"1\">\n");
        stringBuilder.append("<tr>\n");
        for (Field field : Client.class.getDeclaredFields()) {
            stringBuilder.append("<th>");
            stringBuilder.append(field.getName());
            stringBuilder.append("</th>");
        }
        stringBuilder.append("</tr>\n");

        for (Client client: clientList) {
            stringBuilder.append("<tr>\n");
            stringBuilder.append("<td>");
            stringBuilder.append(client.getId());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(client.getFirstName());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(client.getLastName());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(client.getNit());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(client.getPhone());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(client.getAddress());
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(client.getBirthDate());
            stringBuilder.append("</td>");
            stringBuilder.append("</tr>\n");
        }

        stringBuilder.append("</table>\n");

        Writer writer;
        try {
            writer = new FileWriter(Archivo);
            writer.write(stringBuilder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

        return "El reporte " + NombreArchivo + " ha sido generado.";
    }

}
