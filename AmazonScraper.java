import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;

public class AmazonScraper {
    public static void main(String[] args) {
        String searchTerm = "juegos";
        String outputFile = "productos.csv";

        // Construye la URL de búsqueda en Amazon
        String url = "https://www.amazon.es/s?k=juegos&crid=3C475X000VZXG&sprefix=%2Caps%2C14&ref=nb_sb_ss_recent_1_0_recent" + searchTerm;

        try {
            // Realiza la solicitud HTTP y obtiene el documento HTML
            Connection.Response response = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                    .execute();

            Document doc = response.parse();

            // Encuentra todos los elementos de productos en la página
            Elements productElements = doc.select("div[data-component-type='s-search-result']");

            // Crea el archivo de salida CSV
            FileWriter writer = new FileWriter(outputFile);

            // Escribe el encabezado del archivo CSV
            writer.write("Titulo,Precio (EUR)\n");

            // Recorre cada elemento de producto y extrae el título y el precio
            for (Element productElement : productElements) {
                String title = productElement.select("span.a-size-base-plus").text();
                String price = productElement.select("span.a-price-symbol").text() +
                        productElement.select("span.a-price-whole").text() +
                        productElement.select("span.a-price-decimal").text();

                // Escribe el producto en el archivo CSV
                writer.write(title + "," + price + "\n");
            }

            // Cierra el escritor de archivos
            writer.close();

            System.out.println("Se ha generado el archivo " + outputFile + " con los productos encontrados.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


