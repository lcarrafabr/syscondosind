/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.syscondosind.controller;

import br.com.syscondosind.dao.CondominioDAO;
import br.com.syscondosind.dao.EstruturaDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.enumerics.FuncionamentoPortariaEnum;
import br.com.syscondosind.enumerics.TiposPortariasEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.CondominiosVO;
import br.com.syscondosind.vo.EstruturasVO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class EstruturasVIEWController implements Initializable {

    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton btnNovo;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnSalvar;
    @FXML
    private JFXButton btnAlterar;
    @FXML
    private JFXButton btnExcluir;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnLimpar;
    @FXML
    private JFXButton btnSair;
    @FXML
    private JFXCheckBox checkBoxPossuiPortaria;
    @FXML
    private JFXCheckBox checkBoxPossuiElevador;
    @FXML
    private JFXCheckBox checkBoxPossuiCercaEletrica;
    @FXML
    private JFXCheckBox checkBoxPossuiTVFechado;
    @FXML
    private JFXTextField txtQTDApto;
    @FXML
    private JFXTextField txtQTDCobertura;
    @FXML
    private JFXComboBox<String> comboTipoPortaria;
    @FXML
    private JFXComboBox<String> comboFuncionamentoPortaria;
    @FXML
    private JFXTextField txtVagasGaragem;
    @FXML
    private JFXTextField txtQTDElevadores;
    @FXML
    private JFXTextField txtConstrutora;
    @FXML
    private JFXTextField txtAnoConstrucao;
    @FXML
    private JFXCheckBox checkBoxSalaoFesta;
    @FXML
    private JFXCheckBox checkBoxPlayGround;
    @FXML
    private JFXCheckBox checkBoxAguaIndividualizada;
    @FXML
    private JFXCheckBox checkBoxPiscina;
    @FXML
    private JFXCheckBox checkBoxPortaoEletronico;
    @FXML
    private JFXCheckBox checkBoxGasEncanado;
    @FXML
    private JFXCheckBox checkBoxColetaSeletivaLixo;
    @FXML
    private JFXCheckBox checkBoxColetaOleoCozinha;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private JFXTextField txtTaxaCondominio;
    @FXML
    private JFXTextField txtValorFundoReserva;
    @FXML
    private TableView<CondominiosVO> gradeCondominio;
    @FXML
    private TableColumn<CondominiosVO, Integer> colId;
    @FXML
    private TableColumn<CondominiosVO, String> colNomeCondominio;
    @FXML
    private JFXComboBox<String> comboTipoGaragem;

    private int codigoGradeCondominio; // Id usado para liberar o cadastro e saber a qual condominio se refere a lista.

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        renderizaGrade();
        carregaComboTipoPortaria();
        carregaComboFuncionamnetoPortaria();
        carregaComboTipoGaragem();
        getGrade();
        controlMenuButton(ControlButtonsEnum.ABERTURA);
        btnNovo.setDisable(true);
    }

    private void renderizaGrade() {

        colId.setCellValueFactory(new PropertyValueFactory<>("codigoPessoaCondominio"));
        colNomeCondominio.setCellValueFactory(new PropertyValueFactory<>("nomePessoaCondominio"));

    }

    private void getGrade() {

        CondominioDAO dao = new CondominioDAO();

        try {
            List<CondominiosVO> list = dao.findAll();

            gradeCondominio.getItems().clear();
            gradeCondominio.getItems().addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
            Funcoes.messageAlert("Erro ao carregar grade.", Alert.AlertType.ERROR);
        }
    }

    private void getFieldEstrutura() {

        try {

            EstruturaDAO dao = new EstruturaDAO();

            List<EstruturasVO> lista = dao.findEstrutura(codigoGradeCondominio);

            if (lista.isEmpty()) {
                limpaCampos();
                btnNovo.setDisable(false);
            } else {
                btnNovo.setDisable(true);
                lista.stream().forEach((lista1) -> {

                    if (lista1.getPossuiPortaria().equals("SIM")) {
                        checkBoxPossuiPortaria.setSelected(true);
                    } else {
                        checkBoxPossuiPortaria.setSelected(false);
                    }
                    if (lista1.getPossuiElevador().equals("SIM")) {
                        checkBoxPossuiElevador.setSelected(true);
                    } else {
                        checkBoxPossuiElevador.setSelected(false);
                    }
                    if (lista1.getPossuiCercaEletrica().equals("SIM")) {
                        checkBoxPossuiCercaEletrica.setSelected(true);
                    } else {
                        checkBoxPossuiCercaEletrica.setSelected(false);
                    }
                    if (lista1.getPossuiTVFechado().equals("SIM")) {
                        checkBoxPossuiTVFechado.setSelected(true);
                    } else {
                        checkBoxPossuiTVFechado.setSelected(false);
                    }
                    if (lista1.getPossuiSalaoFestas().equals("SIM")) {
                        checkBoxSalaoFesta.setSelected(true);
                    } else {
                        checkBoxSalaoFesta.setSelected(false);
                    }
                    if (lista1.getPossuiPlayground().equals("SIM")) {
                        checkBoxPlayGround.setSelected(true);
                    } else {
                        checkBoxPlayGround.setSelected(false);
                    }
                    if (lista1.getPossuiAguaIndividualizada().equals("SIM")) {
                        checkBoxAguaIndividualizada.setSelected(true);
                    } else {
                        checkBoxAguaIndividualizada.setSelected(false);
                    }
                    if (lista1.getPossuiPiscina().equals("SIM")) {
                        checkBoxPiscina.setSelected(true);
                    } else {
                        checkBoxPiscina.setSelected(false);
                    }
                    if (lista1.getPossuiPortaoEletronico().equals("SIM")) {
                        checkBoxPortaoEletronico.setSelected(true);
                    } else {
                        checkBoxPortaoEletronico.setSelected(false);
                    }
                    if (lista1.getPossuiGasEncanado().equals("SIM")) {
                        checkBoxGasEncanado.setSelected(true);
                    } else {
                        checkBoxGasEncanado.setSelected(false);
                    }
                    if (lista1.getPossuiColetaSeletivaLixo().equals("SIM")) {
                        checkBoxColetaSeletivaLixo.setSelected(true);
                    } else {
                        checkBoxColetaSeletivaLixo.setSelected(false);
                    }
                    if (lista1.getPossuiColetaOleoCozinha().equals("SIM")) {
                        checkBoxColetaOleoCozinha.setSelected(true);
                    } else {
                        checkBoxColetaOleoCozinha.setSelected(false);
                    }

                    txtQTDApto.setText(Integer.toString(lista1.getQtdApto()));
                    txtQTDCobertura.setText(Integer.toString(lista1.getQtdCobertura()));
                    comboTipoPortaria.getSelectionModel().select(lista1.getTipoPortaria());
                    comboFuncionamentoPortaria.getSelectionModel().select(lista1.getFuncionamentoPortaria());
                    txtVagasGaragem.setText(Integer.toString(lista1.getVagasGaragem()));
                    txtQTDElevadores.setText(Integer.toString(lista1.getQtdElevadores()));
                    txtConstrutora.setText(lista1.getNomeConstrutora());
                    txtAnoConstrucao.setText(lista1.getAnoConstrucao());
                    txtDescricao.setText(lista1.getDiferenciais());
                    comboTipoGaragem.getSelectionModel().select(lista1.getTipoGaragem());

                });
            }
        } catch (Exception e) {
            System.err.println(e);
            Funcoes.messageAlert("Erro ao carregar dados", Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getGrade();
    }

    @FXML
    private void novoOnAction(ActionEvent event) {
        limpaCampos();
        controlMenuButton(ControlButtonsEnum.NOVO);
    }

    @FXML
    private void editarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {
        if (comboTipoPortaria.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("O campo Tipo Portaria é obrigatório.", Alert.AlertType.WARNING);
            return;
        }
        if (comboFuncionamentoPortaria.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("O campo Funcionamento Portaria é obrigatório.", Alert.AlertType.WARNING);
            return;
        }
        if (comboTipoGaragem.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("O campo Tipo Garagem é obrigatório.", Alert.AlertType.WARNING);
            return;
        }

        try {

            String possuiPortaria = "NÃO";
            String possuiElevador = "NÃO";
            String possuiCercaEletrica = "NÃO";
            String possuiTVFechado = "NÃO";
            String possuiSalaoFesta = "NÃO";
            String possuiPlayground = "NÃO";
            String possuiAguaIndividualizada = "NÃO";
            String possuiPiscina = "NÃO";
            String possuiPortaoEletronico = "NÃO";
            String possuigasEncanado = "NÃO";
            String possuiColetaSeletivaLixo = "NÃO";
            String possuiColetaOleoCozinha = "NÃO";

            if (checkBoxPossuiPortaria.isSelected()) {
                possuiPortaria = "SIM";
            }
            if (checkBoxPossuiElevador.isSelected()) {
                possuiElevador = "SIM";
            }
            if (checkBoxPossuiCercaEletrica.isSelected()) {
                possuiCercaEletrica = "SIM";
            }
            if (checkBoxPossuiTVFechado.isSelected()) {
                possuiTVFechado = "SIM";
            }
            if (checkBoxSalaoFesta.isSelected()) {
                possuiSalaoFesta = "SIM";
            }
            if (checkBoxPlayGround.isSelected()) {
                possuiPlayground = "SIM";
            }
            if (checkBoxAguaIndividualizada.isSelected()) {
                possuiAguaIndividualizada = "SIM";
            }
            if (checkBoxPiscina.isSelected()) {
                possuiPiscina = "SIM";
            }
            if (checkBoxPortaoEletronico.isSelected()) {
                possuiPortaoEletronico = "SIM";
            }
            if (checkBoxGasEncanado.isSelected()) {
                possuigasEncanado = "SIM";
            }
            if (checkBoxColetaSeletivaLixo.isSelected()) {
                possuiColetaSeletivaLixo = "SIM";
            }
            if (checkBoxColetaOleoCozinha.isSelected()) {
                possuiColetaOleoCozinha = "SIM";
            }

            EstruturasVO oEstruturasVO = new EstruturasVO();

            oEstruturasVO.setCodigoPessoaCondominio(codigoGradeCondominio);
            oEstruturasVO.setPossuiPortaria(possuiPortaria);
            oEstruturasVO.setPossuiElevador(possuiElevador);
            oEstruturasVO.setPossuiCercaEletrica(possuiCercaEletrica);
            oEstruturasVO.setPossuiTVFechado(possuiTVFechado);
            oEstruturasVO.setQtdApto(Integer.parseInt(txtQTDApto.getText().trim()));
            oEstruturasVO.setQtdCobertura(Integer.parseInt(txtQTDCobertura.getText().trim()));
            oEstruturasVO.setTipoPortaria(comboTipoPortaria.getSelectionModel().getSelectedItem());
            oEstruturasVO.setFuncionamentoPortaria(comboFuncionamentoPortaria.getSelectionModel().getSelectedItem());
            oEstruturasVO.setVagasGaragem(Integer.parseInt(txtVagasGaragem.getText().trim()));
            oEstruturasVO.setTipoGaragem(comboTipoGaragem.getSelectionModel().getSelectedItem());
            oEstruturasVO.setQtdElevadores(Integer.parseInt(txtQTDElevadores.getText().trim()));
            oEstruturasVO.setNomeConstrutora(txtConstrutora.getText().trim().toUpperCase());
            oEstruturasVO.setAnoConstrucao(txtAnoConstrucao.getText().trim());
            oEstruturasVO.setAreaPrivativa(0.0);
            oEstruturasVO.setAreaPrivativaCoberta(0.0);
            oEstruturasVO.setAreaComunCoberta(0.0);
            oEstruturasVO.setAreaTotalCoberta(0.0);
            oEstruturasVO.setPossuiSalaoFestas(possuiSalaoFesta);
            oEstruturasVO.setPossuiPlayground(possuiPlayground);
            oEstruturasVO.setPossuiAguaIndividualizada(possuiAguaIndividualizada);
            oEstruturasVO.setPossuiPiscina(possuiPiscina);
            oEstruturasVO.setPossuiPortaoEletronico(possuiPortaoEletronico);
            oEstruturasVO.setPossuiGasEncanado(possuigasEncanado);
            oEstruturasVO.setPossuiColetaSeletivaLixo(possuiColetaSeletivaLixo);
            oEstruturasVO.setPossuiColetaOleoCozinha(possuiColetaOleoCozinha);
            oEstruturasVO.setDiferenciais(txtDescricao.getText().trim());

            EstruturaDAO dao = new EstruturaDAO();
            dao.save(oEstruturasVO);
            
            controlMenuButton(ControlButtonsEnum.SALVAR);
            btnNovo.setDisable(true);

            Funcoes.messageAlert("Registro cadastrado com sucesso.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException | DAOException e) {
            e.printStackTrace();
            Funcoes.messageAlert("Erro ao cadastrar.\n" + e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
         if (comboTipoPortaria.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("O campo Tipo Portaria é obrigatório.", Alert.AlertType.WARNING);
            return;
        }
        if (comboFuncionamentoPortaria.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("O campo Funcionamento Portaria é obrigatório.", Alert.AlertType.WARNING);
            return;
        }
        if (comboTipoGaragem.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("O campo Tipo Garagem é obrigatório.", Alert.AlertType.WARNING);
            return;
        }

        try {

            String possuiPortaria = "NÃO";
            String possuiElevador = "NÃO";
            String possuiCercaEletrica = "NÃO";
            String possuiTVFechado = "NÃO";
            String possuiSalaoFesta = "NÃO";
            String possuiPlayground = "NÃO";
            String possuiAguaIndividualizada = "NÃO";
            String possuiPiscina = "NÃO";
            String possuiPortaoEletronico = "NÃO";
            String possuigasEncanado = "NÃO";
            String possuiColetaSeletivaLixo = "NÃO";
            String possuiColetaOleoCozinha = "NÃO";

            if (checkBoxPossuiPortaria.isSelected()) {
                possuiPortaria = "SIM";
            }
            if (checkBoxPossuiElevador.isSelected()) {
                possuiElevador = "SIM";
            }
            if (checkBoxPossuiCercaEletrica.isSelected()) {
                possuiCercaEletrica = "SIM";
            }
            if (checkBoxPossuiTVFechado.isSelected()) {
                possuiTVFechado = "SIM";
            }
            if (checkBoxSalaoFesta.isSelected()) {
                possuiSalaoFesta = "SIM";
            }
            if (checkBoxPlayGround.isSelected()) {
                possuiPlayground = "SIM";
            }
            if (checkBoxAguaIndividualizada.isSelected()) {
                possuiAguaIndividualizada = "SIM";
            }
            if (checkBoxPiscina.isSelected()) {
                possuiPiscina = "SIM";
            }
            if (checkBoxPortaoEletronico.isSelected()) {
                possuiPortaoEletronico = "SIM";
            }
            if (checkBoxGasEncanado.isSelected()) {
                possuigasEncanado = "SIM";
            }
            if (checkBoxColetaSeletivaLixo.isSelected()) {
                possuiColetaSeletivaLixo = "SIM";
            }
            if (checkBoxColetaOleoCozinha.isSelected()) {
                possuiColetaOleoCozinha = "SIM";
            }

            EstruturasVO oEstruturasVO = new EstruturasVO();

            oEstruturasVO.setCodigoPessoaCondominio(codigoGradeCondominio);
            oEstruturasVO.setPossuiPortaria(possuiPortaria);
            oEstruturasVO.setPossuiElevador(possuiElevador);
            oEstruturasVO.setPossuiCercaEletrica(possuiCercaEletrica);
            oEstruturasVO.setPossuiTVFechado(possuiTVFechado);
            oEstruturasVO.setQtdApto(Integer.parseInt(txtQTDApto.getText().trim()));
            oEstruturasVO.setQtdCobertura(Integer.parseInt(txtQTDCobertura.getText().trim()));
            oEstruturasVO.setTipoPortaria(comboTipoPortaria.getSelectionModel().getSelectedItem());
            oEstruturasVO.setFuncionamentoPortaria(comboFuncionamentoPortaria.getSelectionModel().getSelectedItem());
            oEstruturasVO.setVagasGaragem(Integer.parseInt(txtVagasGaragem.getText().trim()));
            oEstruturasVO.setTipoGaragem(comboTipoGaragem.getSelectionModel().getSelectedItem());
            oEstruturasVO.setQtdElevadores(Integer.parseInt(txtQTDElevadores.getText().trim()));
            oEstruturasVO.setNomeConstrutora(txtConstrutora.getText().trim().toUpperCase());
            oEstruturasVO.setAnoConstrucao(txtAnoConstrucao.getText().trim());
            oEstruturasVO.setAreaPrivativa(0.0);
            oEstruturasVO.setAreaPrivativaCoberta(0.0);
            oEstruturasVO.setAreaComunCoberta(0.0);
            oEstruturasVO.setAreaTotalCoberta(0.0);
            oEstruturasVO.setPossuiSalaoFestas(possuiSalaoFesta);
            oEstruturasVO.setPossuiPlayground(possuiPlayground);
            oEstruturasVO.setPossuiAguaIndividualizada(possuiAguaIndividualizada);
            oEstruturasVO.setPossuiPiscina(possuiPiscina);
            oEstruturasVO.setPossuiPortaoEletronico(possuiPortaoEletronico);
            oEstruturasVO.setPossuiGasEncanado(possuigasEncanado);
            oEstruturasVO.setPossuiColetaSeletivaLixo(possuiColetaSeletivaLixo);
            oEstruturasVO.setPossuiColetaOleoCozinha(possuiColetaOleoCozinha);
            oEstruturasVO.setDiferenciais(txtDescricao.getText().trim());

            EstruturaDAO dao = new EstruturaDAO();
            dao.update(oEstruturasVO);
            
            controlMenuButton(ControlButtonsEnum.ALTERAR);
            btnNovo.setDisable(true);

            Funcoes.messageAlert("Registro cadastrado com sucesso.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException | DAOException e) {
            e.printStackTrace();
            Funcoes.messageAlert("Erro ao cadastrar.\n" + e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void excluirOnAction(ActionEvent event) {
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensagem do sistema");
            alert.setHeaderText(null);
            alert.setContentText("Deseja realmente excluir este registro?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                EstruturasVO oEstrut = new EstruturasVO();

                oEstrut.setCodigoPessoaCondominio(codigoGradeCondominio);

                EstruturaDAO dao = new EstruturaDAO();
                dao.setDelete(oEstrut);

                Funcoes.messageAlert("Registro deletado com sucesso!", Alert.AlertType.INFORMATION);

                getGrade();
                controlMenuButton(ControlButtonsEnum.EXCLUIR);
            }

        } catch (DAOException e) {
            e.printStackTrace();
            Funcoes.messageAlert("Erro ao deletar registro\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cancelarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.CANCELAR);
        btnNovo.setDisable(true);
    }

    @FXML
    private void limparOnAction(ActionEvent event) {
        limpaCampos();
    }

    @FXML
    private void sairOnAction(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    private void carregaComboTipoPortaria() {

        for (TiposPortariasEnum t : TiposPortariasEnum.values()) {

            comboTipoPortaria.getItems().addAll(t.getTipoPortaria());
        }
    }

    private void carregaComboFuncionamnetoPortaria() {

        for (FuncionamentoPortariaEnum f : FuncionamentoPortariaEnum.values()) {
            comboFuncionamentoPortaria.getItems().addAll(f.getHorarioFuncionamento());
        }
    }

    private void carregaComboTipoGaragem() {

        comboTipoGaragem.getItems().addAll("INTERNA", "EXTERNA", "NÃO POSSUI GARAGEM");
    }

    private void limpaCampos() {

        checkBoxPossuiPortaria.setSelected(false);
        checkBoxPossuiElevador.setSelected(false);
        checkBoxPossuiCercaEletrica.setSelected(false);
        checkBoxPossuiTVFechado.setSelected(false);
        txtQTDApto.setText("");
        txtQTDCobertura.setText("");
        comboTipoPortaria.getSelectionModel().select(-1);
        comboFuncionamentoPortaria.getSelectionModel().select(-1);
        txtVagasGaragem.setText("");
        txtQTDElevadores.setText("");
        txtConstrutora.setText("");
        txtAnoConstrucao.setText("");
        checkBoxSalaoFesta.setSelected(false);
        checkBoxPlayGround.setSelected(false);
        checkBoxAguaIndividualizada.setSelected(false);
        checkBoxPiscina.setSelected(false);
        checkBoxPortaoEletronico.setSelected(false);
        checkBoxGasEncanado.setSelected(false);
        checkBoxColetaSeletivaLixo.setSelected(false);
        checkBoxColetaOleoCozinha.setSelected(false);
        txtTaxaCondominio.setText("");
        txtValorFundoReserva.setText("");
        txtDescricao.setText("");
        comboTipoGaragem.getSelectionModel().select(-1);

    }

    @FXML
    private void gradeCondominioOnMouseClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        codigoGradeCondominio = gradeCondominio.getSelectionModel().getSelectedItem().getCodigoPessoaCondominio();

        /*Verifica se existe um cadastro e libera o botão novo*/
        if (codigoGradeCondominio > 0) {
            btnNovo.setDisable(false);
        }
        getFieldEstrutura();

    }
    
    private void controlMenuButton(ControlButtonsEnum controle) {
        /*
         *TRUE - desativa o botao
         *FALSE - ativa o botao
         */
        //controle de botões
        final boolean ativa = false;
        final boolean desativa = true;

        switch (controle) {

            case ABERTURA:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
//                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;

            case NOVO:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(desativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(ativa);
//                btnSalvarMais.setDisable(ativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(ativa);
                break;

            case EDITAR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(desativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
//                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(ativa);
                btnExcluir.setDisable(ativa);
                btnCancelar.setDisable(ativa);
                break;

            case SALVAR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
//                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;

            case ALTERAR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
//                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;

            case EXCLUIR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
//                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;

            case CANCELAR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
//                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;
        }
    }

}
