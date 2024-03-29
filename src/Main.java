import chess.ChessController;
import chess.ChessView;
import chess.views.console.ConsoleView;
import chess.views.gui.GUIView;
import engine.ChessGame;


public class Main
{
    public static void main(String[] args)
    {
        try
        {
            // 1. Création du contrôleur pour gérer le jeu d’échec
            ChessController controller = new ChessGame(); // Instancier un ChessController
            // 2. Création de la vue
            ChessView view = new GUIView(controller); // mode GUI
            //ChessView view = new ConsoleView(controller); // mode Console
            // 3 . Lancement du programme.
            controller.start(view);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
