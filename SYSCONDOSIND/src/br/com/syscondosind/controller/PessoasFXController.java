package br.com.syscondosind.controller;

import br.com.syscondosind.dao.PessoasDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.enumerics.StatusEnum;
import br.com.syscondosind.enumerics.TipoPessoaEnumerics;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.PessoasVO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luciano Carrafa Benfica
 */
public class PessoasFXController implements Initializable {

    @FXML
    private JFXTextField txtNomePessoa;
    @FXML
    private JFXComboBox<String> comboStatus;
    @FXML
    private JFXComboBox<String> comboTipoPessoa;
    @FXML
    private JFXDatePicker dataCadastro;
    @FXML
    private TableView<PessoasVO> gradePessoas;
    @FXML
    private TableColumn<PessoasVO, Integer> colCodigoPessoa;
    @FXML
    private TableColumn<PessoasVO, String> colNomePessoa;
    @FXML
    private TableColumn<PessoasVO, String> colStatus;
    @FXML
    private TableColumn<PessoasVO, String> colDataCadastro;
    @FXML
    private TableColumn<PessoasVO, String> colTipoPessoa;

    public int codigo_pessoa;
    public String nomePessoa;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton btnNovo;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnSalvar;
    @FXML
    private JFXButton btnSalvarMais;
    @FXML
    private JFXButton btnAlterar;
    @FXML
    private JFXButton btnExcluir;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton backFirst;
    @FXML
    private JFXButton backPage;
    @FXML
    private JFXButton next;
    @FXML
    private JFXButton nextLast;
    @FXML
    private JFXTextField txtPaginaAtual;
    @FXML
    private Label indicadorPage;

    /*Indice para */
    private int indiceRegistro = 0;
    private double total_registros;
    private double linhasPorPagina = 2.0;
    private int totalPaginas;
    private int from = 1;
    private int nextPage = 0;
    private String filename;
    private String tipoPessoa;
    public String pegaTipoPesoa;
    
    @FXML
    private ImageView avatarPane;
    @FXML
    private JFXButton btnSair;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregaComboStatus();
        carregaDataAtual();
//        carregaComboTipoPessoa();
        renderizaGrade();
//        getGrade();
        controlMenuButton(ControlButtonsEnum.ABERTURA);
        recebeTotalRegistros();
        montaPaginacao();
        indicadorPage.setText(Funcoes.indicePaginacao(totalPaginas, from));
        txtPaginaAtual.setText("1");

    }

    private void renderizaGrade() {

        colCodigoPessoa.setCellValueFactory(new PropertyValueFactory<>("codigoPessoa"));
        colNomePessoa.setCellValueFactory(new PropertyValueFactory<>("nomePessoa"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataFormatada"));
        colTipoPessoa.setCellValueFactory(new PropertyValueFactory<>("tipoPessoa"));
    }

    private void getGrade() {

        String pesquisaNome = txtNomePessoa.getText();
        if (pesquisaNome.isEmpty()) {
            pesquisaNome = null;
        }

        try {
            PessoasDAO dao = new PessoasDAO();
//            List<PessoasVO> lista = dao.find(pesquisaNome, nextPage, (int) linhasPorPagina);
            List<PessoasVO> lista = dao.find(pesquisaNome, tipoPessoa);

            gradePessoas.getItems().clear();
            gradePessoas.getItems().addAll(lista);

            recebeTotalRegistros();
            montaPaginacao();
            indicadorPage.setText(Funcoes.indicePaginacao(totalPaginas, from));

        } catch (DAOException e) {
            e.printStackTrace();
            Funcoes.messageAlert("Erro ao carregar a grade", Alert.AlertType.ERROR);
        }
    }
    
    private void getGradeFindAll() {

        String pesquisaNome = txtNomePessoa.getText();
        if (pesquisaNome.isEmpty()) {
            pesquisaNome = null;
        }

        try {
            PessoasDAO dao = new PessoasDAO();
//            List<PessoasVO> lista = dao.find(pesquisaNome, nextPage, (int) linhasPorPagina);
            List<PessoasVO> lista = dao.findAll();

            gradePessoas.getItems().clear();
            gradePessoas.getItems().addAll(lista);

        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao carregar a grade\n" +e, Alert.AlertType.ERROR);
        }
    }

    private void recebeTotalRegistros() {

        try {
            PessoasDAO dao = new PessoasDAO();
            List<PessoasVO> lista = dao.totalRegistrosPessoas();

            lista.stream().forEach((lista1) -> {
                total_registros = lista1.getTotalRegistros();
            });
        } catch (DAOException e) {
            System.err.println("Erro\n" + e);
            Funcoes.messageAlert("Erro ao pegar o total de registros\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void getFieldGradePessoa() {

        try {

            PessoasDAO dao = new PessoasDAO();

            codigo_pessoa = gradePessoas.getSelectionModel().getSelectedItem().getCodigoPessoa();
            nomePessoa = gradePessoas.getSelectionModel().getSelectedItem().getNomePessoa();

            List<PessoasVO> lista = dao.findPerson(codigo_pessoa);

            lista.stream().forEach((lista1) -> {

                txtNomePessoa.setText(lista1.getNomePessoa());
                comboStatus.getSelectionModel().select(lista1.getStatus());
                comboTipoPessoa.getSelectionModel().select(lista1.getTipoPessoa());
                dataCadastro.setValue(lista1.getDataCadastro());
                
                pegaTipoPesoa = comboTipoPessoa.getSelectionModel().getSelectedItem();

                try {
                    if (lista1.getAvatar() != null) {
                        carregaImagemTela(lista1.getAvatar());
                    } else {
                        avatarSemImagem();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PessoasFXController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

        } catch (DAOException e) {
            e.printStackTrace();
            Funcoes.messageAlert("Erro ao carregar dados da grade", Alert.AlertType.ERROR);
        }
    }

    private void avatarSemImagem() {

        try {
            Image image = new Image("/resources/image/ImageName.png");

            avatarPane.setPreserveRatio(false);
            avatarPane.setSmooth(true);

            avatarPane.setImage(image);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void carregaImagemTela(Blob avatar) throws SQLException {
        Image image;
        InputStream is = avatar.getBinaryStream();

        try {
            OutputStream os = new FileOutputStream(new File("AVATAR.jpg"));
            byte[] contents = new byte[1024];
            int size = 0;
            while ((size = is.read(contents)) != -1) {
                os.write(contents, 0, size);
            }
            image = new Image("file:AVATAR.jpg");
            avatarPane.setImage(image);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PessoasFXController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PessoasFXController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getGrade();
    }

    @FXML
    private void novoOnAction(ActionEvent event) {
        limparOnAction(event);
        controlMenuButton(ControlButtonsEnum.NOVO);
        txtNomePessoa.requestFocus();

    }

    @FXML
    private void wditarOnAction(ActionEvent event) {

        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {

        if (txtNomePessoa.getText().isEmpty()) {
            Funcoes.messageAlert("O campo nome é obrigatório", Alert.AlertType.WARNING);
            return;
        }
        if (comboStatus.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Escolha um status", Alert.AlertType.WARNING);
            return;
        }
        if (comboTipoPessoa.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Escolha um tipo pessoa", Alert.AlertType.WARNING);
            return;
        }
        if (dataCadastro.getValue() == null) {
            Funcoes.messageAlert("A data de cadastro é obrigatório", Alert.AlertType.WARNING);
            return;
        }

        try {
            PessoasVO oPessoa = new PessoasVO();

            oPessoa.setNomePessoa(txtNomePessoa.getText().trim().toUpperCase());
            oPessoa.setStatus(comboStatus.getSelectionModel().getSelectedItem());
            oPessoa.setTipoPessoa(comboTipoPessoa.getSelectionModel().getSelectedItem());
            oPessoa.setDataCadastro(dataCadastro.getValue());
            
            oPessoa.setOperadorFile(0);

//            filename = null;
            if (!"".equals(filename) || filename != null) {

                oPessoa.setOperadorFile(1);
                oPessoa.setCaminhoImagemPasta(filename);
            }

            PessoasDAO pessoaDAO = new PessoasDAO();
            pessoaDAO.save(oPessoa);
            filename = null;
            oPessoa.setOperadorFile(0);

            Funcoes.messageAlert("Cadastrado com sucesso.", Alert.AlertType.INFORMATION);

            getGrade();

            controlMenuButton(ControlButtonsEnum.SALVAR);

        } catch (DAOException e) {
            e.printStackTrace();
            Funcoes.messageAlert("Erro ao cadastrar", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void salvarMaisOnAction(ActionEvent event) {
        salvarOnAction(event);
        novoOnAction(event);
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
        if (txtNomePessoa.getText().isEmpty()) {
            Funcoes.messageAlert("O campo nome é obrigatório", Alert.AlertType.WARNING);
            return;
        }
        if (comboStatus.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Escolha um status", Alert.AlertType.WARNING);
            return;
        }
        if (comboTipoPessoa.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Escolha um tipo pessoa", Alert.AlertType.WARNING);
            return;
        }
        if (dataCadastro.getValue() == null) {
            Funcoes.messageAlert("A data de cadastro é obrigatório", Alert.AlertType.WARNING);
            return;
        }

        try {

            PessoasVO oPessoa = new PessoasVO();

            oPessoa.setCodigoPessoa(codigo_pessoa);
            oPessoa.setNomePessoa(txtNomePessoa.getText().trim().toUpperCase());
            oPessoa.setStatus(comboStatus.getSelectionModel().getSelectedItem());
            oPessoa.setTipoPessoa(comboTipoPessoa.getSelectionModel().getSelectedItem());
            oPessoa.setDataCadastro(dataCadastro.getValue());
            
            oPessoa.setOperadorFile(0);
            
//            filename = null;
            if (!"".equals(filename) || filename != null) {

                oPessoa.setOperadorFile(1);
                oPessoa.setCaminhoImagemPasta(filename);
            }

            PessoasDAO pessoaDAO = new PessoasDAO();
            pessoaDAO.getAlterar(oPessoa);
            oPessoa.setOperadorFile(0);
            filename = null;

            Funcoes.messageAlert("Registro alterado com sucesso!", Alert.AlertType.INFORMATION);

            getGrade();

            controlMenuButton(ControlButtonsEnum.ALTERAR);

        } catch (DAOException e) {
            e.printStackTrace();
            Funcoes.messageAlert("Erro ao atualizar registro", Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void excluirOnAction(ActionEvent event) {

        try {
            PessoasVO oPessoa = new PessoasVO();

            codigo_pessoa = gradePessoas.getSelectionModel().getSelectedItem().getCodigoPessoa();

            oPessoa.setCodigoPessoa(codigo_pessoa);

            PessoasDAO pessoaDAO = new PessoasDAO();
            pessoaDAO.getDelete(oPessoa);

            Funcoes.messageAlert("Registro deletado com sucesso", Alert.AlertType.INFORMATION);

            getGrade();

            controlMenuButton(ControlButtonsEnum.EXCLUIR);

        } catch (Exception e) {
            e.printStackTrace();
            Funcoes.messageAlert("Erro ao deletar registro\n" + e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cancelarOnAction(ActionEvent event) {

        controlMenuButton(ControlButtonsEnum.CANCELAR);
    }

    @FXML
    private void limparOnAction(ActionEvent event) {

        txtNomePessoa.setText("");
        comboStatus.getSelectionModel().select(-1);
//        comboTipoPessoa.getSelectionModel().select(-1);
        dataCadastro.setValue(LocalDate.now());
        cancelarOnAction(event);
        getGrade();

    }

    @FXML
    private void sairOnAction(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    private void carregaComboStatus() {

        for (StatusEnum s : StatusEnum.values()) {
            comboStatus.getItems().addAll(s.getStatus());
        }
    }

    public void carregaComboTipoPessoa() {

        for (TipoPessoaEnumerics t : TipoPessoaEnumerics.values()) {
            comboTipoPessoa.getItems().addAll(t.getTipoPessoa());
            
        }
        getGradeFindAll();
    }

    private void carregaDataAtual() {
        /**
         * Insere a data Atual no campo dataCadastro A data é no formato
         * LocalDate
         */
        dataCadastro.setValue(Funcoes.getNow());
    }

    @FXML
    private void gradePessoaOnMouseClicked(javafx.scene.input.MouseEvent event) {
        btnEditar.setDisable(false);
        getFieldGradePessoa();
    }

    private void controlMenuButton(ControlButtonsEnum controle) {
        /*
         *TRUE - desativa o botao
         *FALSE - ativa o botao
         */
        final boolean ativa = false;
        final boolean desativa = true;

        switch (controle) {

            case ABERTURA:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;

            case NOVO:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(desativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(ativa);
                btnSalvarMais.setDisable(ativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(ativa);
                break;

            case EDITAR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(desativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(ativa);
                btnExcluir.setDisable(ativa);
                btnCancelar.setDisable(ativa);
                break;

            case SALVAR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;

            case ALTERAR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;

            case EXCLUIR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;

            case CANCELAR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;
        }
    }

    @FXML
    private void nomePessoaKeyReleased(KeyEvent event) {
        getGrade();
    }

    @FXML
    private void backFirstOnAction(ActionEvent event) {

        indiceRegistro = 0;
        System.out.println("indice: " + indiceRegistro);
    }

    @FXML
    private void backPageOnAction(ActionEvent event) {
        if (from > 1) {
//        nextPage = nextPage - (int)(linhasPorPagina + linhasPorPagina);
            nextPage = (int) (nextPage - linhasPorPagina);
            from--;
            Funcoes.indicePaginacao(totalPaginas, from);
            txtPaginaAtual.setText(Integer.toString(from));
            indicadorPage.setText(Funcoes.indicePaginacao(totalPaginas, from));
            System.out.println("next page: " + nextPage);
            getGrade();
        }
    }

    @FXML
    private void nextOnAction(ActionEvent event) {

        if (from < totalPaginas) {
//        nextPage = nextPage + (int)(linhasPorPagina + linhasPorPagina);
            nextPage = (int) (nextPage + linhasPorPagina);
            from++;
            Funcoes.indicePaginacao(totalPaginas, from);
            txtPaginaAtual.setText(Integer.toString(from));
            indicadorPage.setText(Funcoes.indicePaginacao(totalPaginas, from));
            System.out.println("next page: " + nextPage);
            getGrade();
        }
    }

    @FXML
    private void nerxtLastOnAction(ActionEvent event) {
    }

    private void montaPaginacao() {

        totalPaginas = (int) Funcoes.totalPaginas(total_registros, linhasPorPagina);
//        System.out.println("total paginas: " + totalPaginas);

    }

    @FXML
    private void btnCarregarImagemPasta(ActionEvent event) {

        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {

            filename = selectedFile.getAbsolutePath();
//            System.out.println("Caminho: " + selectedFile.getAbsolutePath());
            carregaImagem(filename);

        }
    }

    private void carregaImagem(String path) {

        try {
            Image image = new Image("file:///" + path);

            avatarPane.setPreserveRatio(false);
            avatarPane.setSmooth(true);

            avatarPane.setImage(image);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /*filtra somente pessoas do tipo Jurídico*/
    public void pessoaJuridica(){
        carregaComboTipoPessoa();
        comboTipoPessoa.setDisable(true);
        comboTipoPessoa.getSelectionModel().select(TipoPessoaEnumerics.JURIDICA.getTipoPessoa()); 
        tipoPessoa = TipoPessoaEnumerics.JURIDICA.getTipoPessoa();
        getGrade();
        System.out.println("Tipo pessoa:  " + tipoPessoa);
    }
    public void pessoaFisica(){
        carregaComboTipoPessoa();
        comboTipoPessoa.setDisable(true);
        comboTipoPessoa.getSelectionModel().select(TipoPessoaEnumerics.FISICA.getTipoPessoa()); 
        tipoPessoa = TipoPessoaEnumerics.FISICA.getTipoPessoa();
        getGrade();
//        System.out.println("Tipo pessoa:  " + tipoPessoa);
    }

}
