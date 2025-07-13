public class App {
    public static void main(String[] args) {
        // Launch the WordleSwing GUI on the Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            new Wordle(); 
        });
    }
}
