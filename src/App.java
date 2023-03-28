import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    private static final String BLUE_MEDIUM_BACKGROUND = "\u001b[97;1m \u001b[48;2;42;122;228m";
    private static final String BLUE_BOLD_BACKGROUND = "\u001b[97;1m \u001b[48;2;0;0;128m";
    private static final String RESET = "\u001b[m";

    public static void main(String[] args) throws Exception {

        String opcao = null;// Declara e inicializa a variavel
        String userChoice;// Declara variavel
        try (// escolher a categoria de filme que deseja
                var Lista = new Scanner(System.in)) {
            System.out.println(BLUE_MEDIUM_BACKGROUND
                    + "       Escolha a lista a seguir:      \n\n 1- Top filmes \n 2- Top séries \n 3- Filmes populares \n 4- Séries populares");
            userChoice = Lista.nextLine();// user input
            System.out.println(BLUE_BOLD_BACKGROUND + "       Lista escolhida: " + userChoice + "     " + RESET);
        }
        switch (userChoice) {
            case "1":

                opcao = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
                break;

            case "2":

                opcao = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopTVs.json";
                break;

            case "3":

                opcao = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
                break;

            case "4":

                opcao = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs.json";
                break;

            default:

                System.out.println("\u001b[37m\u001b[41;1m\u001b[1m       Opção invalida          \u001b[m\n");
                break;
        }

        // fazer uma conexão HTTP e buscar
        String url = opcao;// Conexão URL da API, chamada com opcao
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        System.out.println(body);

        // pegar somente os dados que nos interessam (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados

        for (Map<String, String> filme : listaDeFilmes) {
            System.out.println(BLUE_BOLD_BACKGROUND + "Título:" + RESET + " \u001b[97;1m" + filme.get("title"));
            System.out.println("\u001b[97;1m URL da imagem: " + RESET + filme.get("image"));
            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            int starsnumber = (int) Math.round(classificacao / 2);
            System.out
                    .println(
                            "\u001b[97;1m Classificação: " + "\u001b[48;2;0;128;0m " + filme.get("imDbRating") + " "
                                    + RESET);

            for (int n = 1; n <= starsnumber; n++) {

                if (n <= 2) {
                    System.out.print(" ⭐ ");
                } else if (n <= 4) {
                    System.out.print(" ⭐ ");
                } else if (n <= 6) {
                    System.out.print(" ⭐ ");
                } else if (n <= 8) {
                    System.out.print(" ⭐ ");
                } else if (n >= 9) {
                    System.out.print(" ⭐ ");
                }

            }

            for (int n = starsnumber + 1; n <= 5; n++) {
                System.out.print(" ☆ ");

            }

            // QUEBRA DE LINHA
            System.out.println();
            System.out.println();
        }
    }
}
