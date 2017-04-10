package control;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbsController {

    protected Stage currentStage;
    public abstract void logout() throws IOException;
}
