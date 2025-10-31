package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

 class Pair implements Comparable<Pair> {
    private int first;
    private int second;

    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }


    public int getMax(){
        return Math.max(first, second);
    }

    public Boolean equalsWith(int num){
        return first == num && second == num;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return first == pair.first && second == pair.second;
    }

    // @Override
    // public int hashCode() {
    //     return 31 * first + second;
    // }


    @Override
    public String toString() {
        return "(" + first + "," + second + ")";
    }

    @Override
    public int compareTo(Pair other) {
        if (this.first != other.first) {
            return Integer.compare(this.first, other.first);
        }
        return Integer.compare(this.second, other.second);
    }
}


public class App extends Application {

    //ΔΗΛΩΣΕΙΣ ΒΑΣΙΚΩΝ ΑΝΤΙΚΕΙΜΕΝΩΝ

    private Stage welcomeStage;

    VBox mainVBox = new VBox();
    Label titleLabel = new Label("ΠΙΘΑΝΟΤΗΤΕΣ ΚΑΙ ΣΤΑΤΙΣΤΙΚΗ");
    HBox titleLabelHBox = new HBox(titleLabel);
    HBox mainButtonsHBox = new HBox(10);
    Button mainExitButton = new Button("Έξοδος");
    StackPane topicsStackPane = new StackPane();

    ObservableList<String> chapter_1_examples_List = FXCollections.observableArrayList("Παράδειγμα 1.6","Παράδειγμα 1.7","Παράδειγμα 1.8","Παράδειγμα 1.9","Παράδειγμα 1.10","Παράδειγμα 1.11");
    ComboBox<String> chapter_1_examples_ComboBox = new ComboBox<>(chapter_1_examples_List);
    HBox mainInfos_HBox = new HBox(chapter_1_examples_ComboBox);

    VBox example_1_6_VBox = new VBox();
    VBox example_1_7_VBox = new VBox();
    VBox example_1_8_VBox = new VBox();
    VBox example_1_9_VBox = new VBox();
    VBox example_1_10_VBox = new VBox();
    VBox example_1_11_VBox = new VBox();

    TextArea solutionTextArea = new TextArea();
    HBox solutionHBox = new HBox();
    TextFlow solution_TextFlow = new TextFlow();
    List<Pair> b_List_1_7 = new ArrayList<>();
    ComboBox<Integer> m_value_ComboBox = new ComboBox<>();

    ComboBox<String> results_1_8_ComboBox = new ComboBox<>();

    HBox exam_solution_pro_HBox = new HBox();

    ComboBox<String> airplane_Facts_ComboBox = new ComboBox<>();
    ComboBox<String> radar_dedection_Facts_ComboBox = new ComboBox<>();

    String problemText = null;
    String str1_1_9 = null;
    String str2_1_9 = null;
    Text facts_1_9_Text = new Text();

    ImageView image_1_9_ImageView = new ImageView();
    Text solution_1_9_Text = new Text();

    int totalCards = 52;

    Map<String,Integer> ex_1_10_Map = new HashMap<>();

    List<Double> prob_ListToFraction = new ArrayList<>();

    double probability = 0.0;

    boolean flag = false;

    List<Integer> whereTheHeartsIs_List = new ArrayList<>();

    List<String> problems_List = new ArrayList<>();

    public final Path problems_FilePath = Path.of("C:/Users/Asimakis/Documents/ΠΑΝΕΠΙΣΤΗΜΙΟ/ΠΤΥΧΙΟ -ΒΟΗΘΗΤΙΚΑ/ΠΙΘΑΝΟΤΗΤΕΣ _ ΕΡΓΑΣΙΕΣ/ΔΕΣΜΕΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ/reservedprob/problems.txt");

    private static Scene scene;

   @Override
    public void start(Stage stage) throws IOException {

    welcomeGUI();

    PauseTransition delay = new PauseTransition(Duration.seconds(7));
    delay.setOnFinished(event -> {
        welcomeStage.close();

        // Συνέχεια του προγράμματος
        m_value_ComboBox.getItems().addAll(1, 2, 3, 4);
        m_value_ComboBox.setValue(2);
        mainGUI(stage);
    });

    delay.play();



    // updateProblemToProblemsTXT(1);
    // runPythonScript();
}

    public void welcomeGUI(){

        String problem_1_8_str = "Από μία συντηρητική ομάδα σχεδίασης, ας την ονομάσουμε C, και μια πρωτοποριακή ομάδα σχεδίασης,"+
    " ας την ονομάσουμε N,ζητάμε να σχεδιαστεί χωριστά ένα καινούργιο προϊόν μέσα σε ένα μήνα. Από προηγούμενη εμπειρία γνωρίζουμε ότι:" +

                "(α) Η πιθανότητα η μάδα C να είναι επιτυχής είναι 2/3" +

                "(β) Η πιθανότητα η ομάδα N να είναι επιτυχής είναι 1/2" +

                "(γ) Η πιθανότητα τουλάχιστον μία ομάδα να είναι επιτυχής είναι 3/4." +



               "ΑΙΤΗΜΑ ΠΡΟΣ CHATGPT" +

                "Βρες μου τον δειγματικό χώρο. (Να παράξεις μία μη αριθμημένη λίστα από String που να περιέχει ΜΟΝΟ "+
                "την περιγραφή των στοιχείων του δειγματικού χώρου, χωρίς καμία επεξήγηση ή αναφορά στις πιθανότητες"+
                "και στους μαθηματικούς υπολογισμούς. Στην απάντησή σου εμφάνισε ΜΟΝΟ την λύση και ΜΗΝ επαναλαμβάνεις την εκφώνηση του προβλήματος.)\n" ;

    String problem_1_9_str = "Εάν κάποιο αεροσκάφος βρίσκεται σε κάποια περιοχή, ένα ραντάρ " +
                "καταγράφει σωστά την παρουσία του με πιθανότητα 0.99 ." +

                "Εάν δεν βρίσκεται στην περιοχή, το ραντάρ καταγράφει " +
                "λανθασμένα την παρουσία του με πιθανότητα 0.10 ." +
                "Υποθέτουμε ότι ένα αεροσκάφος είναι παρόν με πιθανότητα 0.05 ." +
                "Ποια είναι η πιθανότητα λανθασμένου συναγερμού (λανθασμένη ένδειξη παρουσίας αεροσκάφους), και " +
                "ποια είναι η πιθανότητα έλλειψης ανίχνευσης (καμία ανίχνευση δεν γίνεται παρόλο που το αεροπλάνο είναι παρόν); " + "ΑΙΤΗΜΑ ΠΡΟΣ CHATGPT" +
                "Δώσε μου τα ενδεχόμενα για αεροπλάνο και ραντάρ (χωριστά) MONO, χωρίς εισαγωγικές προτάσεις,να προσδιορίσεις τις πιθανότητες που προκύπτουν. (Όχι πάνω από 200 χαρακτήρες)";

     problems_List.add(problem_1_8_str);

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("problems.txt"), StandardCharsets.UTF_8))) {
            writer.write("1."+problem_1_8_str+"2."+problem_1_9_str);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    // updateProblemToProblemsTXT(0);
    // runPythonScript();
    runSolver(1);

     problems_List.add(problem_1_9_str);

     runSolver(2);

        airplane_Facts_ComboBox.setPromptText("ΠΑΡΟΥΣΙΑ ΑΕΡΟΠΛΑΝΟΥ - ΓΕΓΟΝΟΤΑ");
        airplane_Facts_ComboBox.getItems().addAll("A = Το αεροπλάνο είναι παρόν","Aᶜ = Το αεροπλάνο ΔΕΝ είναι παρόν");
        airplane_Facts_ComboBox.setId("airplane_Facts_ComboBox");
        //airplane_Facts_ComboBox.setStyle("-fx-background-color:white;");

        //ComboBox<String> radar_dedection_Facts_ComboBox = new ComboBox<>();
        radar_dedection_Facts_ComboBox.setPromptText("ΑΝΙΧΝΕΥΣΗ ΡΑΝΤΑΡ - ΓΕΓΟΝΟΤΑ");
        radar_dedection_Facts_ComboBox.getItems().addAll("B = Σωστή ανίχνευση ραντάρ","Bᶜ = Λάθος ανίχνευση ραντάρ");
        radar_dedection_Facts_ComboBox.setId("radar_dedection_Facts_ComboBox");

        //System.out.println("sdasdsadsad 5");

        ImageView imViewAsimakis = new ImageView(new Image("file:/C:/Users/Asimakis/Documents/ΠΑΝΕΠΙΣΤΗΜΙΟ/ΠΤΥΧΙΟ -ΒΟΗΘΗΤΙΚΑ/ΠΙΘΑΝΟΤΗΤΕΣ _ ΕΡΓΑΣΙΕΣ/ΔΕΣΜΕΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ/images/logo.png"));

        //System.out.println("TEST GUI GUI GUI");
        HBox logoTypeHBox = new HBox(imViewAsimakis);
        logoTypeHBox.setAlignment(Pos.TOP_CENTER);
        imViewAsimakis.setFitWidth(400);
        imViewAsimakis.setFitHeight(400);
        imViewAsimakis.setPreserveRatio(true); // Διατηρεί τις αναλογίες
        imViewAsimakis.setSmooth(true); // Κάνει την εικόνα πιο ομαλή
        imViewAsimakis.setCache(true); // Κάνει cache για καλύτερη απόδοση

        //ImageView welcomeImageView = createAnimatedImageView(500);

        HBox imagesHBox = new HBox();
        //magesHBox.getChildren().add(welcomeImageView);
        //imagesHBox.setAlignment(Pos.CENTER);

        VBox welcomeVBox = new VBox(logoTypeHBox,imagesHBox);

        welcomeVBox.setSpacing(10);

        welcomeVBox.setStyle("-fx-background-color:yellowgreen;");



        Scene welcomeScene = new Scene(welcomeVBox,400,400);

        welcomeStage = new Stage();
        welcomeScene.setFill(Color.TRANSPARENT);
        welcomeStage.initStyle(StageStyle.TRANSPARENT);

        Rectangle clip = new Rectangle(400, 400);
        clip.setArcWidth(180);
        clip.setArcHeight(180);
        welcomeVBox.setClip(clip);

        welcomeStage.setScene(welcomeScene);

        welcomeStage.show();

    }

    // Μέθοδος για τα σταθερά εμφανισιακά στοιχεία του κεντρικού παραθύρου
    public void mainGUI(Stage stage){

        chapter_1_examples_ComboBox.setPromptText("Παραδείγματα");
        titleLabel.setAlignment(Pos.TOP_CENTER);
        titleLabelHBox.setAlignment(Pos.CENTER);
        titleLabelHBox.setStyle("-fx-background-color: orange;");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        topicsStackPane.setPrefSize(550, 720);
        topicsStackPane.setStyle("-fx-background-color: lightblue;");
        mainButtonsHBox.setStyle("-fx-background-color: orange;");
        mainInfos_HBox.setStyle("-fx-background-color: lightblue");
        mainInfos_HBox.setPadding(new Insets(0, 10, 0, 10));

        mainButtonsHBox.setPadding(new Insets(10,0,10,0));

        mainButtonsHBox.getChildren().addAll(mainExitButton);
        mainButtonsHBox.setAlignment(Pos.BOTTOM_CENTER);

        solutionTextArea.setStyle("-fx-background-color:beige;");

        chapter_1_examples_ComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {

            switch (newVal) {
                case "Παράδειγμα 1.6":
                    //mainInfos_HBox.setMaxHeight(200);
                    example_1_6_VBox.getChildren().clear();
                    solutionTextArea.clear();
                    topicsStackPane.getChildren().clear();
                    topicsStackPane.setPrefHeight(300);
                    solutionTextArea.setStyle("-fx-font-size:13px;");
                    titleLabel.setText("Κεφάλαιο 1ο - 1.3 ΔΕΣΜΕΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ");
                    stage.setHeight(600);
                    stage.setWidth(550);
                    example_1_6();

                    topicsStackPane.getChildren().add(example_1_6_VBox);

                    StackPane.setAlignment(topicsStackPane, Pos.BOTTOM_CENTER);

                    break;
                case "Παράδειγμα 1.7":
                    example_1_7_VBox.getChildren().clear();
                    titleLabel.setText("Κεφάλαιο 1ο - 1.3 ΔΕΣΜΕΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ");
                    solutionTextArea.clear();
                    //topicsStackPane.setPrefHeight(700);
                    topicsStackPane.getChildren().clear();
                    topicsStackPane.setPrefHeight(380);
                    stage.setWidth(550);
                    topicsStackPane.getChildren().add(example_1_7_VBox);
                    StackPane.setAlignment(topicsStackPane, Pos.BOTTOM_CENTER);
                    stage.setHeight(720);
                    example_1_7();
                    break;

                case "Παράδειγμα 1.8":

                    // clearTextFile("C:/Users/Asimakis/Documents/ΠΑΝΕΠΙΣΤΗΜΙΟ/ΠΤΥΧΙΟ -ΒΟΗΘΗΤΙΚΑ/ΠΙΘΑΝΟΤΗΤΕΣ _ ΕΡΓΑΣΙΕΣ/ΔΕΣΜΕΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ/reservedprob/problems.txt");
                    // clearTextFile("C:/Users/Asimakis/Documents/ΠΑΝΕΠΙΣΤΗΜΙΟ/ΠΤΥΧΙΟ -ΒΟΗΘΗΤΙΚΑ/ΠΙΘΑΝΟΤΗΤΕΣ _ ΕΡΓΑΣΙΕΣ/ΔΕΣΜΕΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ/reservedprob/responses.txt");
                     example_1_8_VBox.getChildren().clear();

                    titleLabel.setText("Κεφάλαιο 1ο - 1.3 ΔΕΣΜΕΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ");
                    solutionTextArea.clear();
                    topicsStackPane.getChildren().clear();
                    topicsStackPane.setPrefHeight(600);
                    stage.setHeight(880);
                    stage.setWidth(550);
                    topicsStackPane.getChildren().add(example_1_8_VBox);
                    StackPane.setAlignment(topicsStackPane, Pos.BOTTOM_CENTER);
                    problemText = "";
                    problemText = "Από μία συντηρητική ομάδα σχεδίασης, ας την ονομάσουμε C, και μια πρωτοποριακή ομάδα σχεδίασης, ας την ονομάσουμε N,ζητάμε να σχεδιαστεί χωριστά ένα καινούργιο προϊόν μέσα σε ένα μήνα. Από προηγούμενη εμπειρία γνωρίζουμε ότι:\r\n" + //
                                                "(α) Η πιθανότητα η μάδα C να είναι επιτυχής είναι 2/3\r\n" + //
                                                "(β) Η πιθανότητα η ομάδα N να είναι επιτυχής είναι 1/2\r\n" + //
                                                "(γ) Η πιθανότητα τουλάχιστον μία ομάδα να είναι επιτυχής είναι 3/4.\r\n" + //
                                                "\r\n" + //
                                                "ΑΙΤΗΜΑ ΠΡΟΣ CHATGPT\r\n" + //
                                                "Βρες μου τον δειγματικό χώρο. (Να παράξεις μία μη αριθμημένη λίστα από String που να περιέχει ΜΟΝΟ την περιγραφή των στοιχείων του δειγματικού χώρου, χωρίς καμία επεξήγηση ή αναφορά στις πιθανότητες και στους μαθηματικούς υπολογισμούς)\"\r\n" ;


                    example_1_8();
                    //runPythonScript();

                    break;

                case "Παράδειγμα 1.9":


                    example_1_9_VBox.getChildren().clear();
                     //runPythonScript();
                    titleLabel.setText("Κεφάλαιο 1ο - 1.3 ΚΑΝΟΝΑΣ ΠΟΛΛΑΠΛΑΣΙΑΣΜΟΥ");
                    solutionTextArea.clear();
                    solution_TextFlow.getChildren().clear();
                    topicsStackPane.getChildren().clear();
                    //topicsStackPane.setPrefHeight(100);
                    stage.setHeight(900);
                    stage.setWidth(600);
                    //example_1_10_VBox.setStyle("-fx-font-size:13px;");
                    topicsStackPane.getChildren().add(example_1_9_VBox);
                    StackPane.setAlignment(topicsStackPane, Pos.BOTTOM_CENTER);
                    problemText = "";

                    problemText = "Εάν κάποιο αεροσκάφος βρίσκεται σε κάποια περιοχή, ένα ραντάρ \nκαταγράφει σωστά την παρουσία του με πιθανότητα 0.99 .\n"+
                                  "Εάν δεν βρίσκεται στην περιοχή, το ραντάρ καταγράφει \nλανθασμένα την παρουσία του με πιθανότητα 0.10 .\nΥποθέτουμε ότι ένα αεροσκάφος είναι παρόν με πιθανότητα 0.05 .\n"+
                                  "Ποια είναι η πιθανότητα λανθασμένου συναγερμού (λανθασμένη ένδειξη παρουσίας αεροσκάφους), και \nποια είναι η πιθανότητα έλλειψης ανίχνευσης (καμία ανίχνευση δεν γίνεται παρόλο που το αεροπλάνο είναι παρόν); \n"+
                                  "ΑΙΤΗΜΑ ΠΡΟΣ CHATGPT\nΔώσε μου τα ενδεχόμενα για αεροπλάνο και ραντάρ (χωριστά) MONO, χωρίς εισαγωγικές προτάσεις, να προσδιορίσεις τις πιθανότητες που προκύπτουν. (Όχι πάνω από 200 χαρακτήρες)";


                    example_1_9();

                    break;

                case "Παράδειγμα 1.10":
                    example_1_10_VBox.getChildren().clear();
                     //runPythonScript();
                    titleLabel.setText("Κεφάλαιο 1ο - 1.3 ΚΑΝΟΝΑΣ ΠΟΛΛΑΠΛΑΣΙΑΣΜΟΥ");
                    solutionTextArea.clear();
                    solution_TextFlow.getChildren().clear();
                    topicsStackPane.getChildren().clear();
                    //topicsStackPane.setPrefHeight(100);
                    stage.setHeight(620);
                    stage.setWidth(870);
                    topicsStackPane.getChildren().add(example_1_10_VBox);
                    StackPane.setAlignment(topicsStackPane, Pos.BOTTOM_CENTER);
                    problemText = "";

                    example_1_10();
                    break;

                case "Παράδειγμα 1.11":
                    example_1_11_VBox.getChildren().clear();
                     //runPythonScript();
                    titleLabel.setText("Κεφάλαιο 1ο - 1.3 ΚΑΝΟΝΑΣ ΠΟΛΛΑΠΛΑΣΙΑΣΜΟΥ");
                    solutionTextArea.clear();
                    solution_TextFlow.getChildren().clear();
                    topicsStackPane.getChildren().clear();
                    //topicsStackPane.setPrefHeight(100);
                    stage.setHeight(560);
                    stage.setWidth(770);
                    topicsStackPane.getChildren().add(example_1_11_VBox);
                    StackPane.setAlignment(topicsStackPane, Pos.BOTTOM_CENTER);
                    problemText = "";

                    example_1_11();
                    break;

                default:
                    stage.setHeight(930);
                    stage.setWidth(550);
            }
        });

        HBox mValue_HBox = new HBox(m_value_ComboBox);

        solutionTextArea.setPrefSize(570, 700);

        solutionTextArea.setWrapText(true);

        solutionHBox.setPadding((new Insets(0,10,10,10)));
        solutionHBox.setAlignment(Pos.BOTTOM_RIGHT);


        exam_solution_pro_HBox.setMinSize(400,80);
        mainVBox.setAlignment(Pos.TOP_CENTER);

        // mainVBox.layoutYProperty().bind(
        // scene.heightProperty().subtract(mainVBox.heightProperty())
        //);

        mainVBox.getChildren().addAll(titleLabelHBox,mainInfos_HBox,topicsStackPane,solutionHBox,mainButtonsHBox);

        scene = new Scene(mainVBox, 590, 830);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        // Ρύθμιση στρογγυλών γωνιών με προσαρμογή μεγέθους
        Rectangle clip = new Rectangle(590, 830);
        clip.setArcWidth(80);
        clip.setArcHeight(80);
        mainVBox.setClip(clip);

        // Προσαρμογή του clip στο μέγεθος της σκηνής
        mainVBox.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            clip.setWidth(newValue.getWidth());
            clip.setHeight(newValue.getHeight());
        });



        scene.getStylesheets().add(getClass().getResource("/comboStyles.css").toExternalForm());

        //stage.getIcons().add(new Image(getClass().getResource("/images/logo.png").toExternalForm()));

        stage.setScene(scene);
        stage.show();

        buttonsActions();
        buttonsGUI(mainButtonsHBox);
    }



    // Ενέργειες κουμπιών
    public void buttonsActions(){
        mainExitButton.setOnAction(e->{
            // clearTextFile("C:/Users/Asimakis/Documents/ΠΑΝΕΠΙΣΤΗΜΙΟ/ΠΤΥΧΙΟ -ΒΟΗΘΗΤΙΚΑ/ΠΙΘΑΝΟΤΗΤΕΣ _ ΕΡΓΑΣΙΕΣ/ΔΕΣΜΕΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ/reservedprob/problems.txt");
            // clearTextFile("C:/Users/Asimakis/Documents/ΠΑΝΕΠΙΣΤΗΜΙΟ/ΠΤΥΧΙΟ -ΒΟΗΘΗΤΙΚΑ/ΠΙΘΑΝΟΤΗΤΕΣ _ ΕΡΓΑΣΙΕΣ/ΔΕΣΜΕΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ/reservedprob/responses.txt");
            clearTextFile("problems.txt");
            clearTextFile("responses.txt");
            //clearResponsesFile();
            System.exit(1);
        });
    }

    //Εμφάνιση κουμπιών
    public void buttonsGUI(HBox simpleHBox){
        for (Node node: simpleHBox.getChildren()){
            if(node instanceof Button){
                Button btn = (Button) node;
                btn.setPrefSize(90, 40);
                btn.setTextFill(Color.CRIMSON);
                btn.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
            }
        }

    }

    //Κεφάλαιο 1 - Δεσμευμένη Πιθανότητα
    public void example_1_6(){

        Text example_1_6a_Text = new Text("Ρίχνουμε ένα αμερόληπτο νόμισμα διαδοχικά");
        ComboBox<Integer> exam_1_6_Text_ComboBox1 = new ComboBox<>();
        exam_1_6_Text_ComboBox1.getItems().addAll(3,5,7);
        exam_1_6_Text_ComboBox1.setValue(3);
        exam_1_6_Text_ComboBox1.setStyle("-fx-font-size:14");

        Text example_1_6β_Text = new Text("φορές."+'\n'+"Θέλουμε να βρούμε την δεσμευμένη πιθανότητα P(A | B) όταν το Α και το Β είναι τα γεγονότα:"+'\n'+"A =");
        ComboBox<String> exam_1_6_Text_ComboBox2 = new ComboBox<>();
        exam_1_6_Text_ComboBox2.getItems().addAll("{Περισσότερες κορώνες από γράμματα}","{Περισσότερα γράμματα από κορώνες}");
        exam_1_6_Text_ComboBox2.setValue("{Περισσότερες κορώνες από γράμματα}");
        Text example_1_6c_Text = new Text('\n'+"B =");
        ComboBox<String> exam_1_6_Text_ComboBox3 = new ComboBox<>();
        exam_1_6_Text_ComboBox3.getItems().addAll("{Η πρώτη ρίψη είναι κορώνα}","{Η πρώτη ρίψη είναι γράμματα}");
        exam_1_6_Text_ComboBox3.setValue("{Η πρώτη ρίψη είναι κορώνα}");

        TextFlow textFlow_1_6 = new TextFlow(example_1_6a_Text,exam_1_6_Text_ComboBox1,example_1_6β_Text,exam_1_6_Text_ComboBox2,example_1_6c_Text,exam_1_6_Text_ComboBox3);
        textFlow_1_6.setPadding(new Insets(0,10,0,10));

        textFlow_1_6.setStyle("-fx-font-size:14;");

        example_1_6_VBox.setPadding(new Insets(0,20,0,20));

        example_1_6_VBox.getChildren().add(textFlow_1_6);

        update_1_6_Solution(chapter_1_examples_ComboBox, exam_1_6_Text_ComboBox1, exam_1_6_Text_ComboBox2, exam_1_6_Text_ComboBox3);




        example_1_6_VBox.setAlignment(Pos.TOP_CENTER);

        exam_1_6_Text_ComboBox1.valueProperty().addListener((obs, oldVal, newVal) -> update_1_6_Solution(chapter_1_examples_ComboBox, exam_1_6_Text_ComboBox1, exam_1_6_Text_ComboBox2, exam_1_6_Text_ComboBox3));
        exam_1_6_Text_ComboBox3.valueProperty().addListener((obs, oldVal, newVal) -> update_1_6_Solution(chapter_1_examples_ComboBox, exam_1_6_Text_ComboBox1, exam_1_6_Text_ComboBox2, exam_1_6_Text_ComboBox3));
        exam_1_6_Text_ComboBox2.valueProperty().addListener((obs, oldVal, newVal) -> update_1_6_Solution(chapter_1_examples_ComboBox, exam_1_6_Text_ComboBox1, exam_1_6_Text_ComboBox2, exam_1_6_Text_ComboBox3));

    }

    public void example_1_7() {
        Text example_1_7_Text = new Text(
            "Ένα αμερόληπτο 4-εδρο ζάρι ρίχνεται δύο φορές,\n" +
            "και τα 16 αποτελέσματα είναι ισοπίθανα. \n" +
            "Έστω ότι X και Y είναι το αποτέλεσμα της 1ης και της 2ης ρίψης αντίστοιχα.\n" +
            "Επιθυμούμε να προσδιορίσουμε την δεσμευμένη πιθανότητα P(A|B) όπου:\n\n" +
            "\t\tA = {max(X,Y) = m},\n\n" +
            "\t\tB = {min(X,Y) = 2},\n\n" +
            "το m παίρνει τιμές m ∈ {1,2,3,4}"
        );

        Label m_value_Label = new Label();

        m_value_ComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
             //m_value_ComboBox.setVisible(true);

             if (newVal != null) {
                m_value_Label.setVisible(false);
                String labelText = " Επομένως, για m = "+String.valueOf(newVal)+" :";

                m_value_Label.setText(labelText);
                m_value_Label.setVisible(true);

                update_1_7_Solution();
             }
        });

        // TextFlow που περιέχει το Text και το ComboBox inline
        TextFlow flow = new TextFlow(example_1_7_Text, m_value_ComboBox,m_value_Label);
        flow.setLineSpacing(5);

        example_1_7_VBox.getChildren().add(flow);
        example_1_7_VBox.setStyle("-fx-font-size: 13px;");
        example_1_7_VBox.setPadding(new Insets(0, 20, 0, 20));

        update_1_7_Solution();
    }

    public void example_1_8(){
        //updateProblemToProblemsTXT();
        TextArea problemTextArea = new TextArea();
        problemTextArea.setWrapText(true);
        problemTextArea.setEditable(false);
        problemTextArea.setPrefSize(480, 300);

        solutionTextArea.setStyle("-fx-font-family: 'Courier'; -fx-font-size: 12;");

       Text problem_1_8_Text = new Text(problemText);
        //Path problemsPath = Path.of(problems_FilePath);

        // try (BufferedReader reader = Files.newBufferedReader(problems_FilePath, StandardCharsets.UTF_8)) {
        //     StringBuilder sb = new StringBuilder();
        //     String line;
        //     while ((line = reader.readLine()) != null) {
        //         sb.append(line).append("\n");
        //     }
        //     problem_1_8_Text.setText(sb.toString());
        // } catch (IOException e) {
        //     problem_1_8_Text.setText("Δεν ήταν δυνατή η ανάγνωση του αρχείου προβλημάτων.");
        // }

        // Δεύτερο Text για το επιπλέον κείμενο
        Text problem_1_8b_Text = new Text(
            "\nΑΙΤΗΜΑ ΠΡΟΣ ΤΟ ΠΡΟΓΡΑΜΜΑ" +
            "\nΑν υποθέσουμε ότι παράγεται ακριβώς ένας επιτυχημένος σχεδιασμός" +
            "\nτότε ποια είναι η πιθανότητα να σχεδιάστηκε από την ομάδα N; "
        );

        // Φτιάχνεις το TextFlow με τα δύο
        TextFlow tf_1_8_textFlow = new TextFlow(problem_1_8_Text, problem_1_8b_Text);
        example_1_8_VBox.getChildren().add(tf_1_8_textFlow);
        example_1_8_VBox.setAlignment(Pos.TOP_CENTER);
        example_1_8_VBox.setPadding(new Insets(10, 20, 10, 20));
        example_1_8_VBox.setStyle(("-fx-font-size:13px;"));
        update_1_8_Solution();
    }

    public void example_1_9(){
        //updateProblemToProblemsTXT(1);
        Text example_1_9_Text = new Text(problemText);
        example_1_9_Text.setStyle("-fx-font-size:12px;");
        TextFlow example_1_9_TextFlow = new TextFlow(example_1_9_Text);
        example_1_9_TextFlow.setPadding(new Insets(20,20,20,20));
         example_1_9_VBox.setAlignment(Pos.TOP_CENTER);
        example_1_9_VBox.getChildren().add(example_1_9_TextFlow);

        update_1_9_Solution();
    }

    //ΔΙΑΦΟΡΕΣ ΜΕΘΟΔΟΙ
    public void example_1_10(){
         //updateProblemToProblemsTXT();
         whereTheHeartsIs_List.clear();
         prob_ListToFraction.clear();
        Text example_1_10a_Text = new Text("Από μία κοινή τράπουλα 52 χαρτιών, επιλέγονται");
        ComboBox<Integer> numOfCards_ComboBox = new ComboBox<>();
        numOfCards_ComboBox.getItems().addAll(1,2,3,4,5,6,7);
        numOfCards_ComboBox.setValue(3);
        numOfCards_ComboBox.setPrefWidth(10);
        Text example_1_10b_Text = new Text("χαρτιά, ");
        ComboBox<String> playAgain_ComboBox = new ComboBox<>();
        playAgain_ComboBox.getItems().addAll("με","χωρίς");
        playAgain_ComboBox.setValue("χωρίς");
        playAgain_ComboBox.setPrefWidth(60);
        Text example_1_10c_Text = new Text("επανατοποθέτηση.\nΕπιθυμούμε να βρούμε την πιθανότητα");
        ComboBox<Integer> numOfHearts_ComboBox = new ComboBox<>();

        for(int i = 0; i <= numOfCards_ComboBox.getValue(); i++){
                numOfHearts_ComboBox.getItems().add(i);
        }

        numOfHearts_ComboBox.setValue(0);

        // numOfCards_ComboBox.valueProperty().addListener((obs,oldValue,newValue)->{
             //numOfHearts_ComboBox.getItems().clear();
        //    // numOfHearts_ComboBox.setValue(0);
        //     for(int i = 0; i <= numOfCards_ComboBox.getValue(); i++){
        //         numOfHearts_ComboBox.getItems().add(i);
        //     }
        //     numOfHearts_ComboBox.setValue(0);
        // });

        //numOfHearts_ComboBox.setValue(0);

        Text example_1_10d_Text = new Text("από τα επιλεγμένα χαρτιά να είναι κούπα. Υποθέτουμε ότι" + //

                        " σε κάθε βήμα, καθένα από τα υπολειπόμενα χαρτιά έχει την ίδια πιθανότητα να επιλεγεί. \n" +
                        "Λόγω συμμετρίας, κάθε τριάδα της τράπουλας είναι ισοπίθανη να επιλεγεί. \n" +
                        "Για την επιλυση, να ακολουθηθεί ακολουθιακή περιγραφή πειραμάτων σε συνδυασμό με τον κανόνα του πολλαπλασιασμού.");
        TextFlow example_1_10_TextFlow = new TextFlow(example_1_10a_Text,numOfCards_ComboBox,example_1_10b_Text,playAgain_ComboBox,example_1_10c_Text,numOfHearts_ComboBox,example_1_10d_Text);



        example_1_10_TextFlow.setStyle("-fx-font-size:13px;");
        //TextFlow example_1_10_TextFlow = new TextFlow(example_1_10_Text);

        example_1_10_TextFlow.setPadding(new Insets(20,20,20,20));
        example_1_10_VBox.setAlignment(Pos.TOP_CENTER);
        example_1_10_VBox.getChildren().add(example_1_10_TextFlow);

        problemText = example_1_10a_Text.toString()+" \n"+String.valueOf(numOfCards_ComboBox.getValue())+" "+
        example_1_10b_Text.toString()+playAgain_ComboBox.getValue()+example_1_10c_Text.toString()+
        String.valueOf(numOfCards_ComboBox.getValue())+example_1_10c_Text.toString()+String.valueOf(numOfHearts_ComboBox.getValue())+example_1_10d_Text;




        //updateProblemToProblemsTXT();
        update_1_10_Solution(numOfCards_ComboBox,playAgain_ComboBox,numOfHearts_ComboBox);

        // playAgain_ComboBox.valueProperty().addListener((obs,oldValue,newValue)->{
        //     totalCardsNum(newValue);
        // });

        numOfCards_ComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if ((newValue != null)&&(newValue != 0)) {
                prob_ListToFraction.clear();
                if(numOfHearts_ComboBox.getValue()!=0){
                    numOfHearts_ComboBox.getItems().clear();
                }

                numOfHearts_ComboBox.getItems().clear();
                for (int i = 0; i <= newValue; i++) {

                    numOfHearts_ComboBox.getItems().add(i);
                }
                numOfHearts_ComboBox.setValue(0);
                update_1_10_Solution(numOfCards_ComboBox, playAgain_ComboBox, numOfHearts_ComboBox);
            }
        });

        playAgain_ComboBox.valueProperty().addListener((obs,oldValue,newValue)->{
            //heartsUpdateProb(newValue);
            update_1_10_Solution(numOfCards_ComboBox, playAgain_ComboBox, numOfHearts_ComboBox);
        });

       numOfHearts_ComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
        //prob_ListToFraction.clear();

            if (newValue != null && newValue > 0) {

                double stageHeight = 0;

                VBox choosewichCard_VBox = new VBox(15);
                choosewichCard_VBox.setAlignment(Pos.TOP_CENTER);

                Label miniTitle_Label = new Label("Επιλογή κούπας");
                HBox miniTitle_HBox = new HBox(miniTitle_Label);

                miniTitle_Label.setStyle("-fx-text-fill: white; -fx-font-weight: bolder;");
                miniTitle_HBox.setAlignment(Pos.TOP_CENTER);
                miniTitle_HBox.setPrefHeight(25);
                miniTitle_HBox.setStyle("-fx-background-color: orange;");

                choosewichCard_VBox.getChildren().add(miniTitle_HBox);

            //====================================================================================================================================
                List<ComboBox<Integer>> choosenHearts_ComboBox_List = new ArrayList<>();

                for(int j = 0; j < numOfHearts_ComboBox.getValue(); j++){
                    HBox choosewichCard_HBox = new HBox(5);
                    choosewichCard_HBox.setAlignment(Pos.CENTER);
                    Label choose_Label = new Label("Επίλεξε τη θέση της κούπας "+(j+1));
                    //System.out.println("DONE_a"+(j+1));
                    ComboBox<Integer> heartCards_ComboBox = new ComboBox<>();
                    for (int i = 0; i < numOfHearts_ComboBox.getItems().size() - 1; i++) {

                        heartCards_ComboBox.getItems().add(i + 1);
                        //System.out.println("DONE_b"+(j+1));
                    }


                    heartCards_ComboBox.setPromptText("-");
                    heartCards_ComboBox.setStyle("-fx-background-color:transparent;");
                    choosewichCard_HBox.getChildren().addAll(choose_Label, heartCards_ComboBox);
                    choosenHearts_ComboBox_List.add(heartCards_ComboBox);
                    if(j > 0){
                        stageHeight += 40.0;
                    }

                    choosewichCard_VBox.getChildren().add(choosewichCard_HBox);
                    whereTheHeartsIs_List.clear();

                    heartCards_ComboBox.valueProperty().addListener((observable,val1,val2)->{


                        whereTheHeartsIs_List.add(val2);
                    });
                    //whereTheHeartsIs_List.add(heartCards_ComboBox.getValue());
                }


                Button chooseHeartCard_Button = new Button("Καταχώρηση");
                chooseHeartCard_Button.setPrefSize(120,30);
                chooseHeartCard_Button.setStyle("-fx-text-fill:green;"+"-fx-font-weight:bolder");

                Button cancel_Button = new Button("Ακύρωση");
                cancel_Button.setPrefSize(80,30);
                cancel_Button.setStyle("-fx-text-fill:maroon;"+"-fx-font-weight:bolder");

                HBox chooseHeartCardButton_HBox = new HBox(100,chooseHeartCard_Button,cancel_Button);
                chooseHeartCardButton_HBox.setPadding(new Insets(15,15,0,15));
                chooseHeartCardButton_HBox.setAlignment(Pos.BOTTOM_CENTER);



                choosewichCard_VBox.getChildren().add(chooseHeartCardButton_HBox);

                choosewichCard_VBox.setAlignment(Pos.TOP_CENTER);

                double vbox_height = stageHeight+165;
                choosewichCard_VBox.setPrefHeight(vbox_height);



                // === ΣΚΗΝΗ & ΠΑΡΑΘΥΡΟ ===
                Stage choosewichCard_Stage = new Stage();
                Scene choosewichCard_Scene = new Scene(choosewichCard_VBox, 350, vbox_height+15);
                choosewichCard_Scene.getStylesheets().add(getClass().getResource("/comboStyles.css").toExternalForm());
                choosewichCard_VBox.setStyle(
                    "-fx-background-color: lightblue; " +
                /*  "-fx-padding: 10px; " +*/
                    "-fx-background-radius: 40px; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.55), 20, 0.6, 3, 0); " +
                    "-fx-font-size: 13px;"

                );

                chooseHeartCard_Button.setOnAction(e2->{

                    System.out.println("ASIMAKIS_TEST2 --> "+whereTheHeartsIs_List);

                    heartsUpdateProb(playAgain_ComboBox.getValue());

                    choosewichCard_Stage.close();
                });
                choosewichCard_Scene.setFill(Color.TRANSPARENT);

                choosewichCard_Stage.initStyle(StageStyle.TRANSPARENT);


                choosewichCard_Stage.initModality(Modality.WINDOW_MODAL);
                choosewichCard_Stage.initOwner(((Stage) numOfHearts_ComboBox.getScene().getWindow()));

                choosewichCard_Stage.setScene(choosewichCard_Scene);
                choosewichCard_Stage.setTitle("Επιλογή Κάρτας");

                Rectangle clip = new Rectangle(335, vbox_height);
                clip.setArcWidth(80);
                clip.setArcHeight(80);
                choosewichCard_VBox.setClip(clip);

                // Προσαρμογή του clip στο μέγεθος της σκηνής
                choosewichCard_VBox.layoutBoundsProperty().addListener((observable, oldVal, newVal) -> {
                    clip.setWidth(newVal.getWidth());
                    clip.setHeight(newVal.getHeight());
                });


                cancel_Button.setOnAction(e->{
                    choosewichCard_Stage.close();
                });

                choosewichCard_Stage.showAndWait();

                update_1_10_Solution(numOfCards_ComboBox, playAgain_ComboBox, numOfHearts_ComboBox);
            }
            if(newValue == null){
                whereTheHeartsIs_List.clear();
                //numOfHearts_ComboBox.getItems().clear();
            }
        });

    }

    public void example_1_11(){
        prob_ListToFraction.clear();
        solutionHBox.getChildren().clear();
        Text problem_1_11_Text_a = new Text("Μία τάξη που αποτελείται από 4 μεταπτυχιακούς και");

        ComboBox<Integer> numOf_ProStudents_ComboBox = new ComboBox<>();
        numOf_ProStudents_ComboBox.getItems().addAll(8,12,16,24);
        numOf_ProStudents_ComboBox.setPrefHeight(10);
        numOf_ProStudents_ComboBox.setValue(12);

        Text problem_1_11_Text_b = new Text("προπτυχιακούς φοιτητές, χωρίζεται τυχαία σε 4 ισάριθμες ομάδες. Ποια είναι η πιθανότητα η κάθε ομάδα να περιλαμβάνει ένα μεταπτυχιακό φοιτητή; "+
        "Με την λέξη τυχαία εννοούμε ότι δεδομένης της τοποθέτησης μερικών φοιτητών σε κάποιες κενές θέσεις,\n"+
        "οποιοσδήποτε φοιτητής που απομένει έχει ίση πιθανότητα να τοποθετηθεί σε οποιαδήποτε από τις υπολειπόμενες θέσεις.");

        TextFlow problem_1_11_TextFlow = new TextFlow(problem_1_11_Text_a,numOf_ProStudents_ComboBox,problem_1_11_Text_b);

        problem_1_11_TextFlow.setStyle("-fx-font-size:13px;");

        problem_1_11_TextFlow.setPadding(new Insets(15,15,15,15));

        example_1_11_VBox.getChildren().add(problem_1_11_TextFlow);

        numOf_ProStudents_ComboBox.valueProperty().addListener((obs,oldValue,newValue)->{
            update_1_11_Solution(numOf_ProStudents_ComboBox);
        });

        update_1_11_Solution(numOf_ProStudents_ComboBox);

    }

    // Συνάρτηση που επιστρέφει ένα HBox με την λύση του παραδείγματος
    public void update_1_6_Solution(ComboBox<String> exampleCombo, ComboBox<Integer> throw_s,ComboBox<String> condition_A,ComboBox<String> condition_B){

        //Label solution_Label = new Label("ΛΥΣΗ = ");


            if(exampleCombo.getValue().equals("Παράδειγμα 1.6")){
                //Εύρεση Δειγματικού Χώρου
                int length = 2 * throw_s.getValue();
                int sample_space_Length = (int)Math.pow(2,throw_s.getValue());
                List<String> sample_space = new ArrayList<>();

                char side_1 = 'Κ';
                char side_2 = 'Γ';
                char[] totalChars = new char[length];


                for(int i = 0; i < length-1; i+=2){
                    totalChars[i] = side_1;
                    totalChars[i+1] = side_2;
                }

                char letter;
                StringBuilder sb = new StringBuilder();

                while(sample_space.size() < sample_space_Length){
                    sb.delete(0, length);
                    Random rd = new Random();
                    for(int k = 0; k < throw_s.getValue(); k++){
                        letter = rd.nextBoolean() ? side_1 : side_2;
                        sb.append(letter);
                    }


                    if(!sample_space.contains(sb.toString())){
                        sample_space.add(sb.toString());
                    }
                }

                sample_space.sort(java.util.Comparator.naturalOrder());
                //System.out.println(sample_space);
                StringBuilder sbb = new StringBuilder();
                for(String str:sample_space){
                    sbb.append(", ").append(str);
                    //solutionLabel.setText(sbb.toString());
                }

                String string_Sbb = sbb.toString().trim();

                string_Sbb = string_Sbb.substring(1);

                //string_Sbb = string_Sbb.replace(',',' ');

                solutionTextArea.setText(" ΔΕΙΓΜΑΤΙΚΟΣ ΧΩΡΟΣ --> Ω = { "+string_Sbb+" }");
                fact_B(condition_B);

                List<String> factB_List = new ArrayList<>();
                for(String str:sample_space){
                    if(condition_B.getValue().equals("{Η πρώτη ρίψη είναι κορώνα}")){
                        if(str.charAt(0) == 'Κ'){
                            factB_List.add(str);
                        }
                    }
                    else{
                        if(str.charAt(0) == 'Γ'){
                            factB_List.add(str);
                        }
                    }
                }

                String fact_b_List_str = factB_List.toString();
                fact_b_List_str = fact_b_List_str.replace('[',' ');
                fact_b_List_str = fact_b_List_str.replace(']',' ');
                fact_b_List_str = fact_b_List_str.trim();

                solutionTextArea.appendText("\n\t\t Β = { "+fact_b_List_str+" }");

                if(sample_space.size()%factB_List.size()!=0){
                    solutionTextArea.appendText("\n\t\tΠιθανότητα P(B) = "+factB_List.size()+"/"+sample_space_Length);
                }
                else{

                    solutionTextArea.appendText("\n\t\tΠιθανότητα P(B) = "+factB_List.size()/gcdFinder(sample_space_Length,factB_List.size())+"/"+sample_space_Length/gcdFinder(sample_space_Length,factB_List.size()));
                }

                double p_B = (double)(factB_List.size()/gcdFinder(sample_space_Length,factB_List.size()))/(sample_space_Length/gcdFinder(sample_space_Length,factB_List.size()));

                solutionTextArea.appendText("\n\n\n\t\tΓΕΓΟΝΟΣ Α ="+condition_A.getValue());
                //System.out.println(factB_List);

                List<String> A_In_B = new ArrayList<>();

                char lett;

                if(condition_A.getValue().equals("{Περισσότερες κορώνες από γράμματα}")){
                    lett = 'Κ';
                }
                else{
                    lett = 'Γ';
                }

                int sumL = 0;
                for(String str:factB_List){
                    sumL = 0;
                    char[] strChar = str.toCharArray();
                    for(int i = 0; i < strChar.length; i++){
                        if(strChar[i] == lett){
                            sumL++;
                        }
                        if(sumL > throw_s.getValue()/2){
                            A_In_B.add(str);
                            break;
                        }
                    }
                }

                String fact_ainb_List_str = A_In_B.toString();
                fact_ainb_List_str = fact_ainb_List_str.replace('[',' ');
                fact_ainb_List_str = fact_ainb_List_str.replace(']',' ');
                fact_ainb_List_str = fact_ainb_List_str.trim();

                solutionTextArea.appendText("\n\t\t A ∩ Β = { "+fact_ainb_List_str+" }");

                //solutionTextArea.appendText("\n\t\tA ∩ B ="+A_In_B);

                if(sample_space.size()%factB_List.size()!=0){
                    solutionTextArea.appendText("\n\t\tΠιθανότητα P(A ∩ B) = "+A_In_B.size()+"/"+sample_space_Length);
                }
                else{

                    solutionTextArea.appendText("\n\t\tΠιθανότητα P(A ∩ B) = "+A_In_B.size()/gcdFinder(sample_space_Length,A_In_B.size())+"/"+sample_space_Length/gcdFinder(sample_space_Length,A_In_B.size()));
                }

                double p_AinB = (double)(A_In_B.size()/gcdFinder(sample_space_Length,A_In_B.size()))/(sample_space_Length/gcdFinder(sample_space_Length,A_In_B.size()));

                double p_AB = (double)p_AinB/p_B;

                solutionTextArea.appendText("\n\n\n\t\tΔΕΣΜΕΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ\n\t\tP(A│B)=  P(A∩B)/P(B) = "+p_AB);

                solutionHBox.getChildren().clear();

                solutionHBox.getChildren().add(solutionTextArea);

            }


            // case "Παράδειγμα 1.7":
            //     solutionTextArea.setText("TEST!!!!!!!!!!!!!!!!!!!!!");


    }

    public void update_1_7_Solution(){
        //solutionTextArea.setText("TEST!!!!!!!!!!!!!!!!!!!!!");
        ex_1_10_Map.clear();
        String str = sampleSpace_1_7(4,2).toString();
        str = str.replace('[',' ');
        str = str.replace(']',' ');
        solutionTextArea.setText("ΔΕΙΓΜΑΤΙΚΟΣ ΧΩΡΟΣ Ω = {"+str+"}");

        // Βρίσκουμε το σύνολο Β
        solutionTextArea.appendText("\n\n\tB = { min(X, Y) = 2 }\n\n\tmin(X,Y) = 2 σημαίνει ότι τουλάχιστον ένα από τα ζάρια είναι 2 και κανένα δεν \n\n\tείναι μικρότερο από 2, επομένως:");

        String str_1_7 = b_List_1_7.toString();
        str_1_7 = str_1_7.replace('[',' ');
        str_1_7 = str_1_7.replace(']',' ');

        solutionTextArea.appendText("\n\n\tB = {"+str_1_7+"}");
        Double posB = (double)b_List_1_7.size()/sampleSpace_1_7(4, 2).size();
        solutionTextArea.appendText("\n\tP(B) = "+posB);

        List<Pair> AinB_1_7_List = new ArrayList<>();

        for(Pair pair: b_List_1_7){
            if(((pair.getMax() == m_value_ComboBox.getValue())|| pair.equalsWith(m_value_ComboBox.getValue()))&&(!AinB_1_7_List.contains(pair))){
                AinB_1_7_List.add(pair);
            }
        }



        if(!AinB_1_7_List.isEmpty()){
            String str_1_7AB = AinB_1_7_List.toString();
            str_1_7AB = str_1_7AB.replace('[',' ');
            str_1_7AB = str_1_7AB.replace(']',' ');
            solutionTextArea.appendText("\n\n\tA ∩ B = {"+str_1_7AB+"}");
        }
        else{
            solutionTextArea.appendText("\n\n\tA ∩ B = ∅");
        }


        double posAB = (double) AinB_1_7_List.size() / sampleSpace_1_7(4, 2).size();

        solutionTextArea.appendText("\n\tΠιθανότητα P(A ∩ B) = " + posAB);

        double posA_lock_B = (double)posAB/posB;

        solutionTextArea.appendText("\n\n\n\t\t\t\t\t\tΔΕΣΜΕΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ\n\t\t\t\t\t\tP(A│B)=  P(A∩B)/P(B) = "+posA_lock_B);

        solutionHBox.getChildren().clear();
        solutionHBox.getChildren().add(solutionTextArea);

    }

    public void update_1_8_Solution(){
        //runPythonScript();
        Path responsesPath = Path.of("responses.txt");
        try (BufferedReader reader = Files.newBufferedReader(responsesPath, StandardCharsets.UTF_8)) {
            Boolean startReading = false;
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {

                if (!startReading && line.startsWith("1")) {
                    startReading = true;
                }

                if(startReading && line.startsWith("2")){
                    break;
                }

                // Αν δεν έχουμε βρει ακόμα τη γραμμή που αρχίζει με "1", συνέχισε να διαβάζεις
                if (!startReading) {
                    continue;
                }

                line = line.substring(2);
                line = line.replace('-', ' ');
                line = line.trim();
                sb.append(line).append("\n");
                results_1_8_ComboBox.getItems().add(line);
            }

            solutionTextArea.setText( "ΔΕΙΓΜΑΤΙΚΟΣ ΧΩΡΟΣ (Απάντηση OpenAI)\n" + sb.toString() + "\nΥΠΟΛΟΓΙΣΜΟΙ ΠΡΟΓΡΑΜΜΑΤΟΣ" );
        } catch (IOException e) {
            solutionTextArea.setText("Δεν ήταν δυνατή η ανάγνωση του αρχείου προβλημάτων.");
        }

        solutionTextArea.appendText("\nΑπό την εκφώνηση γνωρίζουμε ότι:"+"\nP(C) = 2/3\t\t\tP(N) = 1/2\t\t\tP(C∪N) = 3/4,\t\t\n");

        double pC = (double)2/3;
        double pN = (double)1/2;
        double pCuN = (double)3/4;

        solutionTextArea.appendText("Από τον τύπο της Ένωσης γνωρίζουμε ότι: \nP(C∪N) = P(C) + P(N) - P(C∩N), δηλαδή:\nP(C∩N) = P(C) + P(N) - P(C∪N = "+
        doubleToFraction(pC)+" + "+doubleToFraction(pN)+" - "+doubleToFraction(pCuN)+" = "+doubleToFraction(pC+pN-pCuN));
        solutionTextArea.appendText("\n\nΕπομένως η πιθανότητα και η C και η Ν να είχαν επιτυχές σχεδιασμό είναι "+doubleToFraction(pC+pN-pCuN));
        solutionTextArea.appendText("\n\nΗ δεσμευμενη πιθανότητα που ζητάμε είναι η πιθανότητα να υπάρχει ΜΟΝΟ ένας επιτυχημένος σχεδιασμός,και αυτός να είναι της ομάδας Ν, συνεπώς:\n"+
        "Ομάδα C επιτυχής, ομάδα N αποτυχία --> P(C ∩ Nᶜ) = P(C) − P(C ∩ N) = "+doubleToFraction(pC)+" - "+doubleToFraction(pC+pN-pCuN)+" = "+doubleToFraction(pC-pC-pN+pCuN)+"\n");
        solutionTextArea.appendText("Ομάδα N επιτυχής, ομάδα C αποτυχία --> P(N ∩ Cᶜ) = P(N) − P(C ∩ N) = "+doubleToFraction(pN)+" - "+doubleToFraction(pC+pN-pCuN)+" = "+doubleToFraction(pN-pN-pC+pCuN));

        solutionTextArea.appendText("\nΆρα:\nP( Ακριβώς ένας επιτυχημένος σχεδιασμός) = P(C ∩ Nᶜ) + P(N ∩ Cᶜ) = " + doubleToFraction(((pN - (pC + pN - pCuN)) + (pC - (pC + pN - pCuN)))));

        // System.out.println(doubleToFraction(pC+pCuN));
        // System.out.println(doubleToFraction(((pC - (pC + pN - pCuN)) + (pN - (pC + pN - pCuN)))));

        solutionTextArea.appendText(
            "\nΤελικά, η Δεσμευμένη πιθανότητα που ζητάμε είναι:\n\n" +
            "P((N ∩ Cᶜ) | Ακριβώς ένας επιτυχημένος σχεδιασμός) = P(N ∩ Cᶜ) / P(Ακριβώς ένας επιτυχημένος σχεδιασμός) = " +
            doubleToFraction(pN - pN - pC + pCuN) + " / " +
            doubleToFraction((pN - (pC + pN - pCuN)) + (pC - (pC + pN - pCuN))) +
            " = " +
            doubleToFraction((double) ((pN - pN - pC + pCuN) / (((pN - (pC + pN - pCuN)) + (pC - (pC + pN - pCuN)))))) +
            "\n"
        );

        solutionHBox.getChildren().clear();
        solutionHBox.getChildren().add(solutionTextArea);

        //runPythonScript();
    }

    public void update_1_9_Solution(){

        //runPythonScript();
        StringBuilder sb = new StringBuilder();
        int line_sum = 0;

        Path responsesPath = Path.of("responses.txt");
        try (BufferedReader reader = Files.newBufferedReader(responsesPath, StandardCharsets.UTF_8)) {
            Boolean startReading = false;

            String line;
            while ((line = reader.readLine()) != null) {

                if (!startReading && line.startsWith("2")) {
                    startReading = true;
                    line = line.substring(2);
                }

                // Αν δεν έχουμε βρει ακόμα τη γραμμή που αρχίζει με "1", συνέχισε να διαβάζεις
                if (!startReading) {
                    continue;
                }



                //line = line.replace('-', ' ');
                line = line.trim();
                sb.append(line).append("\n");
                //results_1_8_ComboBox.getItems().add(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        solutionHBox.getChildren().clear();
        solution_TextFlow.setStyle("-fx-background-color: white;");

        HBox facts_HBox = new HBox(30);

        Text facts_AI_Text = new Text("ΒΟΗΘΕΙΑ AI\n"+sb.toString());

        System.out.println(facts_AI_Text.toString());

        HBox facts_AI_HBox = new HBox(facts_AI_Text);
        facts_AI_HBox.setPrefWidth(550);

        facts_AI_HBox.setPadding(new Insets(5,15,5,15));
        facts_AI_HBox.setStyle("-fx-background-color:orange");

        facts_HBox.getChildren().addAll(airplane_Facts_ComboBox,radar_dedection_Facts_ComboBox);
        solution_TextFlow.setPadding(new Insets(15,15,15,15));

        //VBox imageAndSolution_VBox = new VBox(20,image_1_9_ImageView,solution_1_9_Text);

        //imageAndSolution_VBox.setAlignment(Pos.CENTER);


        image_1_9_ImageView.setStyle("-fx-padding:15px;");
        solution_TextFlow.getChildren().addAll(facts_AI_HBox,facts_HBox,image_1_9_ImageView,solution_1_9_Text);

        facts_HBox.setStyle("-fx-background-color: white;");
        solution_TextFlow.setId("solution_TextFlow");
        solution_TextFlow.setStyle("-fx-text-alignment: center;");



        example_1_9_VBox.getChildren().addAll(solution_TextFlow);
        //example_1_9_VBox.setSpacing(15);
        example_1_9_VBox.setAlignment(Pos.TOP_CENTER);

        solution_TextFlow.setMinHeight(545);
        solution_TextFlow.setPadding(new Insets(10,10,10,10));

        facts_1_9_Text.setStyle("-fx-font-size:13px; -fx-font-weight:bolder;");

        facts_1_9();

    }

    public void update_1_10_Solution(ComboBox<Integer> totalCardsCombo, ComboBox<String> p_againCombo, ComboBox<Integer> heartsCombo){
        //totalCardsNum(p_againCombo.getValue());
        //heartsCombo.setValue(0);
        //ex_1_10_Map.put(p_againCombo.getValue(), 0);
        //whereTheHeartsIs_List.clear();
        prob_ListToFraction.clear();
        solutionHBox.getChildren().clear();
        HBox solutionHBox_1_10 = new HBox();
        solutionHBox_1_10.setMaxWidth(470);
        solutionHBox_1_10.setPadding(new Insets(10,15,10,15));
        solutionHBox_1_10.setStyle(
            "-fx-background-color: wheat; " +
            "-fx-padding: 18px; " +
            "-fx-background-radius: 60px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 10, 0.3, 0, 0); " +
            "-fx-font-size: 13px;"
        );


        solutionHBox_1_10.setAlignment(Pos.CENTER);

        Text multiply_R = new Text("Ο γενικός κανόνας του πολλαπλασιασμού δηλώνει ότι:\n Η πιθανότητα να συμβούν ταυτόχρονα " +
                        "τα γεγονότα Α₁, Α₂, …, Αₙ είναι:\n" +
                                                "P(A₁ ∩ A₂ ∩ … ∩ Aₙ) = P(A₁) · P(A₂|A₁) · P(A₃|A₁∩A₂) · … · P(Aₙ|A₁∩A₂∩…∩Aₙ₋₁).\n" +
                        "Δηλαδή, το γινόμενο της πιθανότητας του πρώτου γεγονότος με τις υπό συνθήκη πιθανότητες\nκάθε επόμενου, δεδομένων των προηγούμενων.");

        solutionHBox_1_10.getChildren().add(multiply_R);

        //solutionHBox_1_10.setStyle("-fx-background-color:lightblue; font-size:13px;");
        solutionHBox_1_10.setPrefWidth(900);


       // solutionHBox.getChildren().add(solutionHBox_1_10);

        VBox solutionVBox_1_10 = new VBox(15);



        solutionVBox_1_10.getChildren().add(solutionHBox_1_10);
        solutionVBox_1_10.setAlignment(Pos.CENTER);

        HBox solution_1_10b_HBox =  new HBox();

        //=========================================================================
        totalCards = 52;
        int totalHearts = 13;
        String sol_1_10_str = "";

        int lucky_cards = totalCardsCombo.getValue();
        String play_again = p_againCombo.getValue();
        int numOfHearts = heartsCombo.getValue();

        List<String> listOfPos = new ArrayList<>();

        HBox solut_1_10_HBox = new HBox();

        String res_1_10_str = "";


        for (int i = 0; i < lucky_cards; i++) {
            String luckyCardLabel = "A" + (i + 1);


            if (i == 0) {

                res_1_10_str += "P(" + luckyCardLabel + ")";
                //probability = (double) (totalCards - totalHearts) / totalCards;

            } else {
                //res_1_10_str += " * P(" + luckyCardLabel + ")";
                if(play_again.equals("χωρίς")){
                    if(numOfHearts == 0){
                        totalCards--;
                    }
                    else{
                       totalCards = 53-i-1;

                    }
                }


                res_1_10_str += " * P(" + luckyCardLabel + ")";
                //probability = (double) (totalCards - totalHearts) / totalCards;
            }

             //if(!play_again.equals("χωρίς")){
                 probability = (double) (totalCards - totalHearts) / totalCards;
            //}
            // else if((!play_again.equals("με"))&&(flag)){
            //     probability = (double)(totalHearts) / totalCards;
            // }

            System.out.println(probability);

            //if(heartsCombo.getValue().equals(i+1))
            prob_ListToFraction.add(probability);
            //if(heartsCombo.getValue()!=0){
            heartsUpdateProb(play_again);
            //}
            // if(flag == false){
            //     probability = (double) (totalCards - totalHearts + 1) / totalCards;
            //     flag = true;
            // }

            listOfPos.add("P(" + luckyCardLabel + ") = " + doubleToFraction(probability));

            String probability_str = doubleToFraction(probability);
            // System.out.println(probability_str);

            // ex_1_10_Map.put(probability_str,(i+1));



            //System.out.println(prob_ListToFraction.toString());
            //res_1_10_str += "P(" + luckyCardLabel + ") * ";


        }
        listOfPos.add(res_1_10_str);

        for (String str : listOfPos) {
            //System.out.println(str);

            if(str.equals(listOfPos.get(listOfPos.size()-1))){
                sol_1_10_str += str;
                //sol_1_10_str = "\n"+sol_1_10_str;
            }
            else{
                sol_1_10_str += str + "\n";
            }

        }

        double finalResult = 1;
        String multiply_probs_Str = "";
        for (int i = 0; i < prob_ListToFraction.size(); i++) {
            double prob = prob_ListToFraction.get(i);

            //System.out.println(prob);
            if (i == prob_ListToFraction.size() - 1) {
                multiply_probs_Str += ("( " + (doubleToFraction(prob) + " )"));
            } else {
                multiply_probs_Str += ("( " + (doubleToFraction(prob) + " )" + " * "));
            }
            finalResult *= prob;
        }

        //System.out.println("FINAL RESULT ---> "+finalResult);

        String formatted = String.format("%.4f", finalResult);
        String formattedPercent = String.format("%.2f", finalResult * 100);

        Text solut_1_10_Text = new Text();

        // System.out.println("multiply_probs_Str ---> "+multiply_probs_Str);
        // System.out.println("formated --> "+formatted);


        solut_1_10_Text.setText("\nΕπομένως, βάση του κανόνα του πολλαπλασιασμού ισχύει ότι: \n\n"+sol_1_10_str+
        " = "+multiply_probs_Str+" = "+formatted+"  ή "+formattedPercent+"%");



        solut_1_10_Text.setStyle("-fx-font-size:13px");
        solution_1_10b_HBox.getChildren().add(solut_1_10_Text);

        // System.out.println("ASIMAKIS_TEST--> "+multiply_probs_Str);
        // System.out.println("=====================================================================================");

        //=========================================================================
        solutionVBox_1_10.getChildren().add(solution_1_10b_HBox);
        solutionHBox.setAlignment(Pos.CENTER);
        solutionHBox.getChildren().add(solutionVBox_1_10);
    }

    public void update_1_11_Solution(ComboBox<Integer> numOf_Studies_combo) {
        solutionHBox.getChildren().clear();

        Label start_solution_1_11_Label = new Label(
            "Η πιθανότητα ο πρώτος μεταπτυχιακός φοιτητής που θα τοποθετηθεί, να \n" +
            "τοποθετηθεί σε ομάδα που δεν περιέχει άλλον μεταπτυχιακό φοιτητή είναι:\n P (A1) = 1"
        );
        start_solution_1_11_Label.setPadding(new Insets(0, 15, 0, 15));

        HBox start_solution_1_11_HBox = new HBox(start_solution_1_11_Label);
        start_solution_1_11_HBox.setMinHeight(80);
        start_solution_1_11_HBox.setAlignment(Pos.CENTER);
        start_solution_1_11_HBox.setStyle(
            "-fx-background-color: wheat; " +
            "-fx-background-radius: 60px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 10, 0.3, 0, 0); " +
            "-fx-font-size: 12px;"
        );

        Text solution_1_11_Text = new Text("\nΕπομένως οι αντίστοιχες πιθανότητες για τους υπόλοιπους μεταπτυχιακούς φοιτητές,\n" +
                "βάση των κενών θέσεων όπως προκύπτουν κατά την διάρκεια του πειράματος, έχουν ως εξής:");

        int numUndergrads = numOf_Studies_combo.getValue();
        int totalSeats = numUndergrads + 4;                // σύνολο θέσεων
        int teamSize = totalSeats / 4;                     // μέγεθος ομάδας

        solution_1_11_Text.setText(solution_1_11_Text.getText() +
                "\nΔιαθέσιμες θέσεις αρχικά: " + totalSeats +
                "\nΔιαθέσιμες θέσεις μετά την τοποθέτηση του 1ου μετ/κου φοιτητή: " + (totalSeats - 1));

        String str = "";
        double multNum = 1;
        String praxis_str = "";


        for (int i = 2; i <= 4; i++) {

            int freeGroups = 4 - (i - 1);
            int availableSeats = freeGroups * teamSize; // διαθέσιμες θέσεις σε ελεύθερες ομάδες
            int remainingSeats = totalSeats - (i - 1); // συνολικές εναπομείνασες θέσεις

            double prob = (double) availableSeats / remainingSeats;
            multNum *= prob;

            String sstr = "";
            for (int k = 1; k <= i; k++) sstr += k + ",";

            str += "\nA" + i + " = {Μετά την τοποθέτηση του " + i + "ου μετ/κου φοιτητή, οι μετ/κοι φοιτητές " +
                    sstr + " είναι σε διαφορετικές ομάδες} ------> P(A" + i + ") = " + doubleToFraction(prob);

            praxis_str += doubleToFraction(prob) + " * ";
        }

        praxis_str = praxis_str.substring(0, praxis_str.length() - 2);

        String multNum_s = String.format("%.4f", multNum);
        String newstr = String.format("%.2f", multNum * 100);
        String stringMult = multNum_s + " ή " + newstr + "%";

        str += "\n\n\t\t\t\tΖΗΤΟΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ = " + praxis_str + " = " + stringMult;

        solution_1_11_Text.setText(solution_1_11_Text.getText() + str);

        VBox solution_1_11_VBox = new VBox(10, start_solution_1_11_HBox, solution_1_11_Text);
        solutionHBox.setAlignment(Pos.CENTER);
        solutionHBox.getChildren().addAll(solution_1_11_VBox);


    }

    private void updateFactsText() {
        if (str1_1_9 != null && str2_1_9 != null) {
            facts_1_9_Text.setText("\n\tΓΕΓΟΝΟΣ = " + str1_1_9 + " - " + str2_1_9+"\n");
            facts_1_9_Text.setVisible(true);
        }
    }

    public String facts_AI_answer(){
         Path responsesPath = Path.of("responses.txt");
         StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(responsesPath, StandardCharsets.UTF_8)) {

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
                line = line.replace('-', ' ');
                line = line.trim();
                //results_1_8_ComboBox.getItems().add(line);
            }


        } catch (IOException e) {
            solutionTextArea.setText("Δεν ήταν δυνατή η ανάγνωση του αρχείου προβλημάτων.");
        }
        return sb.toString();
    }

//





    public void facts_1_9() {
        facts_1_9_Text.setVisible(false);

        airplane_Facts_ComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                str1_1_9 = newVal;
                //System.out.println(str1_1_9);
                //updateFactsText();
                imageView_1_9_updater(image_1_9_ImageView);

            }
        });

        radar_dedection_Facts_ComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                str2_1_9 = newVal;
                //System.out.println(str2_1_9);
                //updateFactsText();
                imageView_1_9_updater(image_1_9_ImageView);
            }
        });
    }

    public void imageView_1_9_updater(ImageView imageView){
        String v1 = airplane_Facts_ComboBox.getValue();
        String v2 = radar_dedection_Facts_ComboBox.getValue();
        imageView.setFitHeight(270);
        imageView.setFitWidth(430);

        if (v1 != null && v2 != null) {
            if (v1.equals("A = Το αεροπλάνο είναι παρόν") && v2.equals("B = Σωστή ανίχνευση ραντάρ")) {
                imageView.setImage(new Image(getClass().getResource("/images/A1_B1.png").toExternalForm()));
                solution_1_9_Text.setText("\nΑπό την εκφώνηση ισχύει ότι P ( B | A ) = 0.99\tκαι\t P ( A ) = 0.05\tεπομένως:\nΌταν ένα αεροσκάφος βρίσκεται στην περιοχή του ραντάρ με πιθανότητα 0.05 και\n το ραντάρ ανιχνεύει σωστά την παρουσία του με πιθανότητα 0.99 ,\nη πιθανότητα να ανιχνευτεί αεροσκάφος από το ραντάρ είναι:\nP ( B ∩ A) = P ( B | A ) * P ( A ) = 0.99 * 0.05 = 0.0495");
            } else if (v1.equals("A = Το αεροπλάνο είναι παρόν") && v2.equals("Bᶜ = Λάθος ανίχνευση ραντάρ")) {
                imageView.setImage(new Image(getClass().getResource("/images/A1_B2.png").toExternalForm()));
                imageView.setFitHeight(220);
                solution_1_9_Text.setText("\n" + //
                                        "Από την εκφώνηση ισχύει ότι P ( Bᶜ | A ) = 0.01\tκαι\t P ( A ) = 0.05\tεπομένως:\n" + //
                                        "Όταν ένα αεροσκάφος βρίσκεται στην περιοχή του ραντάρ με πιθανότητα 0.05 και\n" + //
                                        " το ραντάρ ΔΕΝ ανιχνεύει την παρουσία του με πιθανότητα 0.01 ,\n" + //
                                        "η πιθανότητα το αεροσκάφος να ΜΗΝ ανιχνευτεί από το ραντάρ είναι:\n" + //
                                        "P ( Bᶜ ∩ A) = P ( Bᶜ | A ) * P ( A ) = 0.01 * 0.05 = 0.0005");
            } else if (v1.equals("Aᶜ = Το αεροπλάνο ΔΕΝ είναι παρόν") && v2.equals("B = Σωστή ανίχνευση ραντάρ")) {
                //imageView.setImage(new Image("/images/A2_B1.png"));
                //System.out.println("ERRORRRRRRRRRRR");

                URL url = getClass().getResource("/images/A2_B1.png");
                System.out.println("URL = " + url);


                imageView.setImage(new Image(getClass().getResource("/images/A2_B1.png").toExternalForm()));
                solution_1_9_Text.setText("\n" + //
                                        "Από την εκφώνηση ισχύει ότι P ( B | Aᶜ ) = 0.90\tκαι\t P ( Aᶜ ) = 0.95\tεπομένως:\n" + //
                                        "Όταν ένα αεροσκάφος ΔΕΝ βρίσκεται στην περιοχή του ραντάρ με πιθανότητα 0.95 και\n" + //
                                        " το ραντάρ ΔΕΝ ανιχνεύει παρουσία αεροσκάφους (λειτουργεί σωστά) με πιθανότητα 0.90 ,\n" + //
                                        "η πιθανότητα να ΜΗΝ ανιχνευτεί τίποτα από το ραντάρ είναι:\n" + //
                                        "P ( B ∩ Aᶜ) = P ( B | Aᶜ ) * P ( Aᶜ ) = 0.90 * 0.95 = 0.855");
            } else if (v1.equals("Aᶜ = Το αεροπλάνο ΔΕΝ είναι παρόν") && v2.equals("Bᶜ = Λάθος ανίχνευση ραντάρ")) {
                //imageView.setImage(new Image("/images/A2_B2.png"));
                imageView.setImage(new Image(getClass().getResource("/images/A2_B2.png").toExternalForm()));
                solution_1_9_Text.setText("\n" + //
                                        "Από την εκφώνηση ισχύει ότι P ( Bᶜ | Aᶜ ) = 0.10\tκαι\t P ( Aᶜ ) = 0.95\tεπομένως:\n" + //
                                        "Όταν ένα αεροσκάφος ΔΕΝ βρίσκεται στην περιοχή του ραντάρ με πιθανότητα 0.95 και\n" + //
                                        " το ραντάρ ανιχνεύει παρουσία αεροσκάφους (λειτουργεί λάθος) με πιθανότητα 0.10 ,\n" + //
                                        "η πιθανότητα λανθασμένης ένδειξης παρουσίας αεροσκάφους είναι:\n" + //
                                        "P ( Bᶜ ∩ Aᶜ) = P ( Bᶜ | Aᶜ ) * P ( Aᶜ ) = 0.10 * 0.95 = 0.095");
            }
            else{
                System.out.println("WRONG !!!!!!!!!!!!!!!!!");
            }
        }
    }

    // Μέθοδος που δέχεται ως παράμετρο έναν double αριθμό, και επιστρέφει (με μια προκαθορισμένη αποδοχή σφάλματος),
    // το αντίστοιχο αλφαριθμητικό (String) σε μορφή κλάσματος.
    // Παράδειγμα --> Στο αποτέλεσμα της διαίρεσης 3 / 4 (0.75) η συνάρτηση μου επιστρέφει το "3 / 4" ή αλλιώς doubleToFraction((double)(3/4)) = "3 / 4"
    public String doubleToFraction(double num) {
        //Αποδεκτό σφάλμα
        double epsilon = 1e-9;


        for (int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 100; j++) {
                double val = (double) i / j;
                if (Math.abs(val - num) < epsilon) {

                    int gcd = gcdFinder(i, j);
                    return (i / gcd) + "/" + (j / gcd);
                }
            }
        }
        return null;
    }




   public void updateProblemToProblemsTXT() {


        // Εάν το αρχείο problems.txt δεν υπάρχει δημιούργησέ το
        if (!Files.exists(problems_FilePath)) {
            try {
                Files.createFile(problems_FilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Εγγραφή του κειμένου στο αρχείο (αντικατάσταση περιεχομένων με UTF-8)
        try {
            Files.writeString(
                    problems_FilePath,
                    problemText,
                    StandardCharsets.UTF_8,
                    java.nio.file.StandardOpenOption.TRUNCATE_EXISTING,
                    java.nio.file.StandardOpenOption.WRITE
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearResponsesFile() {
        File file = new File("C:/Users/Asimakis/Documents/ΠΑΝΕΠΙΣΤΗΜΙΟ/BACK_UP_PROJECTS/ΠΙΘΑΝΟΤΗΤΕΣ _ ΕΡΓΟ/ΔΕΣΜΕΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ/reservedprob/responses.txt");
        try (FileWriter fw = new FileWriter(file, false)) {
            //false = overwrite mode (διαγράφει τα παλιά περιεχόμενα)
            fw.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public List<Pair> sampleSpace_1_7(int dice_seats, int numThrows) {
        List<Pair> sample_spaceList = new ArrayList<>();

        // Δημιουργούμε ΟΛΑ τα δυνατά ζεύγη
        for (int i = 1; i <= dice_seats; i++) {
            for (int j = 1; j <= dice_seats; j++) {
                Pair pairOfNumbers = new Pair(i, j);
                if((!sample_spaceList.contains(pairOfNumbers)))
                    sample_spaceList.add(pairOfNumbers);

                // Αν το min = 2 τότε αποθήκευσε στο b_List_1_7
                if ((Math.min(i, j) == 2)&&(!b_List_1_7.contains(pairOfNumbers))) {
                    b_List_1_7.add(pairOfNumbers);
                }
            }
        }


        sample_spaceList.sort(Comparator.naturalOrder());
        b_List_1_7.sort(Comparator.naturalOrder());

        return sample_spaceList;
    }


    public void fact_B(ComboBox<String> condition_B){
        String con_B_str = condition_B.getValue().substring(1, condition_B.getValue().length() - 1);
       // ΕΥΡΕΣΗ ΣΥΝΟΛΟΥ ΓΕΓΟΝΟΤΟΣ Β

                solutionTextArea.appendText("\n\n\n\t\tΓΕΓΟΝΟΣ Β = "+con_B_str);
    }


    // ΕΥΡΕΣΗ ΜΕΓΙΣΤΟΥ ΚΟΙΝΟΥ ΔΙΑΙΡΕΤΗ (για num1 > num2)
    public int gcdFinder(int num1,int num2){
        if (num2 == 0) {
            return num1; // gcd(num,0) = num
        }

        return gcdFinder(num2,num1%num2);
    }

    // Συνάρτηση που εκτελεί python script κατά την εκτέλεση του προγράμματος

private void runPythonScript() {
    try {
        // Ορισμός διαδρομής του Python script

        String pythonScript = "C:/Users/Asimakis/Documents/ΠΑΝΕΠΙΣΤΗΜΙΟ/BACK_UP_PROJECTS/ΠΙΘΑΝΟΤΗΤΕΣ _ ΕΡΓΟ/ΔΕΣΜΕΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ/reservedprob/open_AI_pro1.py";

        // Δημιουργία ProcessBuilder
        ProcessBuilder pb = new ProcessBuilder("python", pythonScript);

        // Ενώνουμε stdout + stderr
        pb.redirectErrorStream(true);

        // Εξασφαλίζουμε UTF-8 έξοδο
        pb.environment().put("PYTHONIOENCODING", "utf-8");

        // Εκκίνηση διεργασίας
        Process process = pb.start();

        // Διαβάζουμε την έξοδο από το Python script
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            System.out.println("=== Εκτέλεση Python Script ===");
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // εμφάνιση debug output
            }
        }

        // Περιμένουμε να ολοκληρωθεί το script πριν συνεχίσουμε
        int exitCode = process.waitFor();
        System.out.println("Python script ολοκληρώθηκε με κωδικό: " + exitCode);

        if (exitCode == 0) {
            System.out.println("Το responses.txt ενημερώθηκε επιτυχώς!");
        } else {
            System.err.println("Πρόβλημα κατά την εκτέλεση του Python script.");
        }

    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
}



/**
     * Διαγράφει όλα τα περιεχόμενα ενός αρχείου .txt με βάση τη διαδρομή του.
     * Εμφανίζει ενημερωτικό μήνυμα (Alert) σε JavaFX περιβάλλον.
     *
     * @param filePath Η πλήρης διαδρομή του αρχείου (π.χ. "C:/data/test.txt")
     */
    public static void clearTextFile(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            //showAlert("Σφάλμα", "Δεν δόθηκε έγκυρη διαδρομή αρχείου.");
            return;
        }

        File file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            //showAlert("Σφάλμα", "Το αρχείο δεν βρέθηκε ή δεν είναι έγκυρο.");
            return;
        }

        try (FileWriter writer = new FileWriter(file, false)) {
            // false -> αντικαθιστά πλήρως το περιεχόμενο του αρχείου (το "αδειάζει")
            writer.write("");
            //showAlert("Επιτυχία", "Το αρχείο καθαρίστηκε επιτυχώς!");
        } catch (IOException e) {
            //showAlert("Σφάλμα", "Αποτυχία καθαρισμού αρχείου:\n" + e.getMessage());
        }
    }

    // // Βοηθητική μέθοδος για εμφάνιση Alert σε JavaFX
    // private static void showAlert(String title, String message) {
    //     Platform.runLater(() -> {
    //         Alert alert = new Alert(AlertType.INFORMATION);
    //         alert.setTitle(title);
    //         alert.setHeaderText(null);
    //         alert.setContentText(message);
    //         alert.showAndWait();
    //     });
    // }

    public void heartsUpdateProb(String play){
    //int sum = 0;
    //

        double oldVal = probability;
        double newVal = 0;

        int sum = 52;
        int q1 = 39;
        int q2 = 13;

        //if(play.equals("χωρίς")){
            for(int i = 0; i<prob_ListToFraction.size(); i++){
                //sum = q1 + q2;
                flag = true;
                probability = oldVal;
               // if(whereTheHeartsIs_List.size()<2){
                if(whereTheHeartsIs_List.contains(i+1)){
                    if(play.equals("χωρίς")){
                        newVal = (double)(q2 / (double)(sum));
                    }
                    else{
                         newVal = 0.25;
                    }
                    //System.out.println("ΝΕΑ ΤΙΜΗ: "+newVal+'\n'+doubleToFraction(newVal));
                    prob_ListToFraction.set(i, newVal);
                    probability = newVal;
                    q2--;
                    flag = false;
                    //System.out.println("FLAG = "+flag);

                }
                else{
                    if(play.equals("χωρίς")){
                        probability = (double)(sum - q2)/sum;
                        prob_ListToFraction.set(i, probability);
                    }
                    else{
                        probability = 0.75;
                        prob_ListToFraction.set(i, probability);
                    }
                }
                sum--;
            }


         //System.out.println("NEW PROBS ---> "+prob_ListToFraction);
    }

    // ΣΥΝΑΡΤΗΣΗ ΕΠΙΚΟΙΝΩΝΙΑΣ ΜΕ ΠΡΟΓΡΑΜΜΑ PYTHON
    public  void runSolver(int problemNumber) {
        try {
            // Εκτέλεση Python script με το πρόβλημα
            ProcessBuilder pb = new ProcessBuilder("python", "C:/Users/Asimakis/Documents/ΠΑΝΕΠΙΣΤΗΜΙΟ/BACK_UP_PROJECTS/ΠΙΘΑΝΟΤΗΤΕΣ_ΕΡΓΟ/ΔΕΣΜΕΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ/reservedprob/solver.py", String.valueOf(problemNumber));
            pb.inheritIO(); // Εμφάνιση εξόδου στην κονσόλα
            Process process = pb.start();
            process.waitFor();

            System.out.println("Η επίλυση ολοκληρώθηκε!");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch();
    }

}
