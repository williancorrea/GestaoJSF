package br.com.wcorrea.bean;

import br.com.wcorrea.modelo.Pessoa.*;
import br.com.wcorrea.modelo.Universidade;
import br.com.wcorrea.modelo.filtros.FiltroGlobal;
import br.com.wcorrea.repository.EstudanteRepository;
import br.com.wcorrea.repository.PessoaRepository;
import br.com.wcorrea.repository.UniversidadeRepository;
import br.com.wcorrea.util.jpa.Transacional;
import br.com.wcorrea.util.jsf.FacesUtils;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class EstudanteBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Inject
    private Estudante estudante;

    @Getter
    @Setter
    @Inject
    private FiltroGlobal filtro;

    @Inject
    private EstudanteRepository estudanteRepository;

    @Getter
    @Setter
    private LazyDataModel<Estudante> model;

    @Getter
    private List<Universidade> universidadeList;

    @Inject
    private UniversidadeRepository universidadeRepository;

    @Inject
    private PessoaRepository pessoaRepository;


    @Setter
    @Getter
    private UploadedFile file;

    @Getter
    private String imagemCarregada;

    @Getter
    @Setter
    private CroppedImage croppedImage;


    /**
     * METODO EXEXUTADO LOGO APOS A RENDERIZACAO DA TELA
     */
    @PostConstruct
    public void inicio() {
    }

    public void inicializarCadastro() {
        if (estudante == null) {
            novo();
        }
        universidadeList = universidadeRepository.listarTudo();
    }

    public void listarEstudantesCadastradas() {
        if (FacesUtils.isNotPostback()) {
            /**
             * FAZ O CARREGAMENTO LAZYLOADING DE TODAS AS AUDITORIAS
             * CADASTRADAS
             */
            model = new LazyDataModel<Estudante>() {
                private static final long serialVersionUID = 1L;

                @Override
                public List<Estudante> load(int primeiroRegistro, int quantidadeRegistros, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                    //Ordenacao
                    filtro.setPropriedadeOrdenacao("descricao");
                    filtro.setAscendente(true);

                    //Paginacao
                    filtro.setPrimeiroRegistro(primeiroRegistro);
                    filtro.setQuantidadeRegistros(quantidadeRegistros);

                    // Quantidade Maxima de Registros por pagina
                    setRowCount(estudanteRepository.quantidadeRegistrosFiltrados(filtro));
                    return estudanteRepository.listar(filtro);
                }
            };
        }
    }

    /**
     * CRIAR UM NOVO ITEM
     */
    public void novo() {
        estudante = new Estudante();
        estudante.setPessoa(new Pessoa());
        estudante.getPessoa().setPessoaTipo(PessoaTipo.FISICA);
        estudante.getPessoa().setPessoaFisica(new PessoaFisica());
        estudante.getPessoa().getPessoaFisica().setSexo(PessoaSexo.MAXCULINO);
    }

    public void limparFiltros() {
        if (filtro.getPesquisaGlobal().isEmpty()) {
            return;
        }
        filtro.setPesquisaGlobal("");
        listarEstudantesCadastradas();
    }

    /**
     * SALVAR OBJETO NO BANCO DO DADOS
     */
    @Transacional
    public void salvar() {
        boolean editando = estudante.isEditando();
        estudante.getPessoa().getPessoaFisica().setPessoa(estudante.getPessoa());

        if (!estudante.isEditando()) {
            Pessoa pessoa = pessoaRepository.buscarCPF(estudante.getPessoa().getPessoaFisica().getCpf());
            if (pessoa != null || pessoa.getId() == estudante.getPessoa().getId()) {
                if (pessoa.getPessoaFisica().getCpf().equalsIgnoreCase(estudante.getPessoa().getPessoaFisica().getCpf())) {
                    FacesUtils.addMessageErro(estudante.getPessoa().getPessoaFisica().getCpf() + " - " + FacesUtils.mensagemInternacionalizada("erro_cpf_esta_sendo_utilizado"), false);
                    return;
                }
            }
        }

        estudante = estudanteRepository.salvar(estudante);

        if (editando) {
            FacesUtils.addMessageinfo(estudante.getPessoa().getNome() + " " + FacesUtils.mensagemInternacionalizada("informativo_atualizado_sucesso"), false);
        } else {
            FacesUtils.addMessageinfo(estudante.getPessoa().getNome() + " " + FacesUtils.mensagemInternacionalizada("informativo_cadastrada_sucesso"), false);
        }
        novo();
    }

    /**
     * EXCLUIR ITEM DO BANCO DE DADOS
     *
     * @param obj
     */
    @Transacional
    public void excluir(Estudante obj) {
        estudanteRepository.remover(obj.getId());
        FacesUtils.addMessageinfo(obj.getPessoa().getNome() + " " + FacesUtils.mensagemInternacionalizada("informativo_exlusao_sucesso"), false);
        listarEstudantesCadastradas();
    }

    public PessoaSexo[] getPessoaSexo() {
        return PessoaSexo.values();
    }


    public void crop() {
        if (croppedImage == null) {
            return;
        }

        String newFileName = "";
//        setNewImageName(getRandomImageName());
//        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        String newFileName = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" +
//                File.separator + "images" + File.separator + "crop" + File.separator + getNewImageName() + ".jpg";

        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(croppedImage.getBytes(), 0, croppedImage.getBytes().length);
            imageOutput.close();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cropping failed."));
            return;
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Cropping finished."));
    }

    public void upload() {
        if (file != null) {
//            imagemCarregada = ImageHelper.encodeFileToBase64Binary(new File(file.getFileName()));
//            imagemCarregada = new DefaultStreamedContent(new ByteArrayInputStream(file.getInputstream()),"image/png");

//            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
//            FacesContext.getCurrentInstance().addMessage(null, message);


//            FacesMessage msg = new FacesMessage(file.getFileName() + " foi enviado com sucesso.");
//            FacesContext.getCurrentInstance().addMessage(null, msg);
            // Do what you want with the file
            try {
                byte[] foto = file.getContents();
//                String nomeArquivo = file.getFileName();
                //TODO: COLOCAR NOME DO ARQUIVO UNICO (PARA CADA USUARIO) PARA NAO TER PROBLEMA COM ESPACO NO SERVIDOR
                String nomeArquivo = "TESTE_NOME.jpg";
//                FacesContext facesContext = FacesContext.getCurrentInstance();
//                ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();

//                String arquivo = scontext.getRealPath("java.io.tmpdir" + nomeArquivo);
                String arquivo = System.getProperty("java.io.tmpdir") + File.separator + nomeArquivo;
                imagemCarregada = System.getProperty("java.io.tmpdir") + File.separator + nomeArquivo;

//            String arquivo = scontext.getContextPath()+"/uploadis/" + nomeArquivo;
                File f = new File(arquivo);
                if (!f.getParentFile().exists()) f.getParentFile().mkdirs();
                if (!f.exists())
                    f.createNewFile();
                System.out.println(f.getAbsolutePath());
                FileOutputStream fos = new FileOutputStream(arquivo);
                fos.write(foto);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        System.out.println("TESTE");
//        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
