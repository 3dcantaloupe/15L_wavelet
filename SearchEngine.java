import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList < String > Strings = new ArrayList < String > ();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return Strings.toString();

        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    Strings.add(parameters[1]);
                    return String.format("String " + Strings.get(Strings.size()-1) + " added!");
                }
            }
            else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String query = parameters[1];
                    ArrayList < String > results = new ArrayList < String > ();
                    for (int i = 0; i < Strings.size(); i++) {
                        if (Strings.get(i).contains(query)) {
                            results.add(Strings.get(i));
                        }
                    }
                    return results.toString();
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
