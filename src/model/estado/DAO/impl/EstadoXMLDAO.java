package model.estado.DAO.impl;

import model.estado.DAO.EstadoDAO;
import model.estado.Estado;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EstadoXMLDAO implements EstadoDAO {

    private String diretorioXML = "database/xml/estados";


    @Override
    public void cadastrar(Estado estado) {

        try {
            DocumentBuilderFactory builderFac = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = builderFac.newDocumentBuilder();

            Document document = builder.newDocument();
            Element elementEstado = document.createElement("estado");
            document.appendChild(elementEstado);

            Element elementID = document.createElement("id");
            elementID.setTextContent(estado.getId().toString());
            elementEstado.appendChild(elementID);

            Element elementNome = document.createElement("nome");
            elementNome.setTextContent(estado.getNome());
            elementEstado.appendChild(elementNome);

            Element elementSigla = document.createElement("sigla");
            elementSigla.setTextContent(estado.getSigla().toString());;
            elementEstado.appendChild(elementSigla);

            Element elementPais = document.createElement("pais");
            elementPais.setTextContent(estado.getPais().toString());;
            elementEstado.appendChild(elementPais);


            File file = new File(diretorioXML, estado.getId().toString() + ".xml");
            try (FileOutputStream fileOS = new FileOutputStream(file)){

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(fileOS);

                transformer.transform(source, result);

            } catch (IOException | TransformerException ex){
                throw new RuntimeException(ex);
            }

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Estado> listar() {


        FilenameFilter filter = (dir, name) -> name.endsWith(".xml");
        File diretorioRaiz = new File(diretorioXML);
        List<Estado> estados = new ArrayList<>();
        for (File arquivo : diretorioRaiz.listFiles(filter)){
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                Document document = builder.parse(arquivo);

                Element elementEstado = document.getDocumentElement();
                Node elementId = elementEstado.getElementsByTagName("id").item(0);
                Node elementNome = elementEstado.getElementsByTagName("nome").item(0);
                Node elementSigla = elementEstado.getElementsByTagName("sigla").item(0);
                Node elementPais = elementEstado.getElementsByTagName("pais").item(0);


                Estado estado =  new Estado();
                estado.setId(UUID.fromString(elementId.getTextContent()));
                estado.setNome(elementNome.getTextContent());
                estado.setSigla(elementSigla.getTextContent());
                estado.setPais(elementPais.getTextContent());

                estados.add(estado);


            } catch (ParserConfigurationException | SAXException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return estados;
    }

    @Override
    public Estado buscar(UUID id) {


        File arquivo = new File(diretorioXML, id.toString()+".xml");

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            Document document = builder.parse(arquivo);
            Element elementEstado = document.getDocumentElement();
            Node elementId = elementEstado.getElementsByTagName("id").item(0);
            Node elementNome = elementEstado.getElementsByTagName("nome").item(0);
            Node elementSigla = elementEstado.getElementsByTagName("sigla").item(0);
            Node elementPais = elementEstado.getElementsByTagName("pais").item(0);

            Estado estado = new Estado();
            estado.setId(UUID.fromString(elementId.getTextContent()));
            estado.setNome(elementNome.getTextContent());
            estado.setSigla(elementSigla.getTextContent());
            estado.setPais(elementPais.getTextContent());

            return estado;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public void atualizar(UUID id, Estado estado) {

        File arquivo = new File(diretorioXML, id.toString() + ".xml");
        arquivo.delete();

        estado.setId(id);

        cadastrar(estado);


    }

    @Override
    public Estado apagar(UUID id) {


        Estado estado =  buscar(id);

        if (estado!=null) {
            File arquivo = new File(diretorioXML, id.toString() + ".xml");
            arquivo.delete();
        }

        return estado;
    }
}
