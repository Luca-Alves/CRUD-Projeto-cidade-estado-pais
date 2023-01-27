package model.cidade.DAO.impl;

import model.cidade.Cidade;
import model.cidade.DAO.CidadeDAO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CidadeXMLDAO implements CidadeDAO {

   private String diretorioXML = "database/xml/cidades";

    @Override
    public void cadastrar(Cidade cidade) {
        try {
            DocumentBuilderFactory builderFac = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = builderFac.newDocumentBuilder();

            Document document = builder.newDocument();
            Element elementCidade = document.createElement("cidade");
            document.appendChild(elementCidade);

            Element elementID = document.createElement("id");
            elementID.setTextContent(cidade.getId().toString());
            elementCidade.appendChild(elementID);

            Element elementNome = document.createElement("nome");
            elementNome.setTextContent(cidade.getNome());
            elementCidade.appendChild(elementNome);

            Element elementEstado = document.createElement("estado");
            elementEstado.setTextContent(cidade.getEstado().toString());;
            elementCidade.appendChild(elementEstado);


            File file = new File(diretorioXML, cidade.getId().toString() + ".xml");
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
    public List<Cidade> listar() {

        FilenameFilter filter = (dir, name) -> name.endsWith(".xml");
        File diretorioRaiz = new File(diretorioXML);
        List<Cidade> cidades = new ArrayList<>();
        for (File arquivo : diretorioRaiz.listFiles(filter)){
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                Document document = builder.parse(arquivo);

                Element elementCidade = document.getDocumentElement();
                Node elementId = elementCidade.getElementsByTagName("id").item(0);
                Node elementNome = elementCidade.getElementsByTagName("nome").item(0);
                Node elementEstado = elementCidade.getElementsByTagName("estado").item(0);


                Cidade cidade =  new Cidade();
                cidade.setId(UUID.fromString(elementId.getTextContent()));
                cidade.setNome(elementNome.getTextContent());
                cidade.setEstado(elementEstado.getTextContent());

                cidades.add(cidade);


            } catch (ParserConfigurationException | SAXException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return cidades;
    }

    @Override
    public Cidade buscar(UUID id) {

        File arquivo = new File(diretorioXML, id.toString()+".xml");

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            Document document = builder.parse(arquivo);
            Element elementCidade = document.getDocumentElement();
            Node elementId = elementCidade.getElementsByTagName("id").item(0);
            Node elementNome = elementCidade.getElementsByTagName("nome").item(0);
            Node elementEstado = elementCidade.getElementsByTagName("estado").item(0);

            Cidade cidade = new Cidade();
            cidade.setId(UUID.fromString(elementId.getTextContent()));
            cidade.setNome(elementNome.getTextContent());
            cidade.setEstado(elementEstado.getTextContent());

            return cidade;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public void atualizar(UUID id, Cidade cidade) {

        File arquivo = new File(diretorioXML, id.toString() + ".xml");
        arquivo.delete();

        cidade.setId(id);

        cadastrar(cidade);

    }

    @Override
    public Cidade apagar(UUID id) {

        Cidade cidade =  buscar(id);

        if (cidade!=null) {
            File arquivo = new File(diretorioXML, id.toString() + ".xml");
            arquivo.delete();
        }

        return cidade;
    }
}
