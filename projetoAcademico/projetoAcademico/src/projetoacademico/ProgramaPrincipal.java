package ProjetoAcademico;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author coelho
 */
public class ProgramaPrincipal {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private static final int TOTAL_ALUNOS = 1000;

    private static final String DIRETOR_ENSINO = "Pâmela Perini";
    private static final String COORDENADOR_ENSINO = "Vitor Valente";

    private static final int OP_ALUNO = 1;
    private static final int OP_PROFESSOR = 2;
    private static final int OP_ENSINO = 3;
    private static final int OP_SAIR = 4;

    private static final int OP_ALUNO_VER_CURSOS = 1;
    private static final int OP_ALUNO_VER_NOTAS = 2;
    private static final int OP_ALUNO_VOLTAR = 3;

    private static final int OP_PROFESSOR_DAR_NOTAS = 1;
    private static final int OP_PROFESSOR_ALTERAR_NOTA = 2;
    private static final int OP_PROFESSOR_ADICIONAR_AREA = 3;
    private static final int OP_PROFESSOR_REMOVER_AREA = 4;
    private static final int OP_PROFESSOR_VOLTAR = 5;

    private static final int OP_ENSINO_NOVO_ALUNO = 1;
    private static final int OP_ENSINO_NOVO_CURSO = 2;
    private static final int OP_ENSINO_NOVA_DISCIPLINA = 3;
    private static final int OP_ENSINO_NOVO_PROFESSOR = 4;
    private static final int OP_ENSINO_VOLTAR = 5;

    private static final int OP_DISCIPLINA_SAIR = 1;
    private static final int OP_DISCIPLINA_NOVA = 2;

    private static final int POSICAO_INVALIDA = -1;

    public static void main(String[] args) throws IOException {
        
        ArrayList<Aluno> alunos = new ArrayList<>();
        SetorEnsino ensino = new SetorEnsino(DIRETOR_ENSINO, COORDENADOR_ENSINO);
        
        try{
            File f = new File("IFRS.bin");
            if(!f.exists()){       
                criarArquivo(new Dados(ensino, alunos), f);
            }
            else{
                Dados dados = lerArquivo(f);
                
                ensino = dados.getEnsino();
                alunos = dados.getAlunos();
            }
        }catch(IOException e){
            System.out.println("Algo deu errado");
            File log = new File("log.txt");
            if(!log.exists()){
                log.createNewFile();
            }
            FileWriter fw = new FileWriter(log);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println("Algo deu Errado");
        }
        

        int opcao = 4;

        do {
            opcao = menu("MENU 1: \n "
                    + "[" + OP_ALUNO + "] Aluno \n "
                    + "[" + OP_PROFESSOR + "] Professor \n "
                    + "[" + OP_ENSINO + "] Setor de Ensino \n "
                    + "[" + OP_SAIR + "] Sair \n",
                    br);
            switch (opcao) {
                case OP_ALUNO:
                    menu_alunos("MENU 2: \n"
                            + "[" + OP_ALUNO_VER_CURSOS + "] Ver Cursos \n"
                            + "[" + OP_ALUNO_VER_NOTAS + "] Ver notas \n"
                            + "[" + OP_ALUNO_VOLTAR + "] Voltar",
                            ensino,
                            alunos,
                            br);

                    break;
                case OP_PROFESSOR:
                    System.out.println("Qual o seu número de siape, professor?");
                    int siape = Integer.parseInt(br.readLine());
                    int posicao_professor = login_professor(siape, ensino, br);

                    if (posicao_professor != POSICAO_INVALIDA) {
                        menu_professor("MENU 2: \n "
                                + "[" + OP_PROFESSOR_DAR_NOTAS + "] Dar Notas de uma disciplina \n "
                                + "[" + OP_PROFESSOR_ALTERAR_NOTA + "] Alterar uma nota \n "
                                + "[" + OP_PROFESSOR_ADICIONAR_AREA + "] Adicionar Área \n "
                                + "[" + OP_PROFESSOR_REMOVER_AREA + "] Remover Área \n"
                                + "[" + OP_PROFESSOR_VOLTAR + "] Voltar",
                                posicao_professor,
                                ensino,
                                br
                        );
                    } else {
                        System.out.println("SIAPE inválido.");
                    }
                    break;
                case OP_ENSINO:
                    menu_ensino("MENU 2: \n "
                            + "[" + OP_ENSINO_NOVO_ALUNO + "] Cadastrar Aluno \n "
                            + "[" + OP_ENSINO_NOVO_CURSO + "] Cadastrar Curso \n "
                            + "[" + OP_ENSINO_NOVA_DISCIPLINA + "] Adicionar Disciplina ao Curso \n "
                            + "[" + OP_ENSINO_NOVO_PROFESSOR + "] Cadastrar Professor \n"
                            + "[" + OP_ENSINO_VOLTAR + "] Voltar",
                            ensino,
                            alunos,
                            br);

            }
        } while (opcao != OP_SAIR);
    }

    
    //////////////////// ARQUIVO ////////////////////////////////////////////////
    private static void criarArquivo(Dados dados, File f) throws IOException{
        f.createNewFile();
        try(FileOutputStream fos = new FileOutputStream(f); ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(dados);
        }catch(IOException e){
            System.out.println("Não foi possível criar a pasta");
            File log = new File("log.txt");
            if(!log.exists()){
                log.createNewFile();
            }
            FileWriter fw = new FileWriter(log);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println("Algo deu Errado");
        } 
    }
    
    private static Dados lerArquivo(File f) throws FileNotFoundException, IOException{
        Dados dados = null;
        
        try(FileInputStream fis = new FileInputStream(f); ObjectInputStream ois = new ObjectInputStream(fis)){
            dados = (Dados) ois.readObject();
        }catch(ClassNotFoundException ex){
            System.out.println("Erro ao ler arquivo");
            File log = new File("log.txt");
            if(!log.exists()){
                log.createNewFile();
            }
            FileWriter fw = new FileWriter(log);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println("Algo deu Errado");            
        }
        return dados;
    }
    
    ////////////////// ENSINO ///////////////////////////////////////////
    private static void menu_ensino(String opcoes, SetorEnsino ensino, ArrayList<Aluno> alunos, BufferedReader br) throws IOException {
        int opcao = menu(opcoes, br);

        switch (opcao) {
            case OP_ENSINO_NOVA_DISCIPLINA:
                System.out.println("Qual o curso?");
                String nome_curso = br.readLine();
                Curso c = encontra_curso(ensino, nome_curso);

                if (c == null) {
                    System.err.println("Curso não encontrado. Cadastre-o.");
                    c = cadastra_curso(ensino, br);
                }
                System.out.println("Qual o professor?");
                String nome_professor = br.readLine();
                Professor p = encontra_professor(ensino, nome_professor);

                if (p == null) {
                    System.err.println("Professor não encontrado. Cadastre-o.");
                    p = cadastra_professor(br, ensino);
                }
                if (cadastra_disciplina(ensino, c, p, br)) {
                    System.out.println("Disciplina cadastrada com sucesso.");
                } else {
                    System.err.println("O limite de disciplinas foi excedido.");
                }
                break;
            case OP_ENSINO_NOVO_ALUNO:
                novo_aluno(ensino, alunos, br);
                break;
            case OP_ENSINO_NOVO_CURSO:
                cadastra_curso(ensino, br);
                break;
            case OP_ENSINO_NOVO_PROFESSOR:
                cadastra_professor(br, ensino);
                break;
        }
    }

    private static int menu(String opcoes, BufferedReader br) throws IOException {
        System.out.println(opcoes);
        String texto = br.readLine();

        int opcao = Integer.parseInt(texto);
        return opcao;
    }

    ////////////////// ALUNO ///////////////////////////////////////////
    private static void menu_alunos(String opcoes, SetorEnsino ensino, ArrayList<Aluno> alunos, BufferedReader br) throws IOException {
        int opcao = menu(opcoes, br);

        switch (opcao) {
            case OP_ALUNO_VER_CURSOS:
                ver_cursos(ensino);
                break;
            case OP_ALUNO_VER_NOTAS:
                System.out.println("Qual a sua matrícula, caro discente?");
                long matricula = Long.parseLong(br.readLine());

                ver_notas(ensino, alunos, matricula);
                break;
        }
    }

    private static void novo_aluno(SetorEnsino ensino, ArrayList<Aluno> alunos, BufferedReader br) throws IOException {
        Aluno a = cadastra_aluno(ensino, br, alunos);

        cadastra_disciplinas_aluno(br, ensino, a);
    }

    private static void ver_notas(SetorEnsino ensino, ArrayList<Aluno> alunos, long matricula) {
        HashMap<String, HashMap<String, HashMap<Long, Float>>> listanotas = obterNotas(ensino);
    }
    
    private static HashMap<String, HashMap<String, HashMap<Long, Float>>> obterNotas(SetorEnsino ensino){
        HashMap<Long, Float> nomenota = new HashMap();
        HashMap<String, HashMap<Long, Float>> discalunos = new HashMap();
        HashMap<String, HashMap<String, HashMap<Long, Float>>> cursodisc = new HashMap();
        for(Curso cursos : ensino.getCursos()){
            Curso cursotemp = cursos;
            for(Disciplina disciplinas : cursotemp.getDisciplinas()){
                Disciplina disctemp = disciplinas;
                nomenota = disctemp.getNotas();
                discalunos.put(disctemp.getNome(), nomenota);
            }
            cursodisc.put(cursotemp.getNome(), discalunos);
        }
        return cursodisc;
    }

    private static Aluno cadastra_aluno(SetorEnsino ensino, BufferedReader br, ArrayList<Aluno> alunos) throws IOException {
        Aluno a = cria_aluno(ensino, br);

        for (int i = 0; i < alunos.size(); i++) {
            Aluno aluno = alunos.get(i);

            if (aluno == null) {
                alunos.set(i, a);
            }
        }
        return a;
    }

    private static Aluno cria_aluno(SetorEnsino ensino, BufferedReader br) throws IOException {
        Aluno a = new Aluno();

        System.out.println("Nome:");
        a.setNome(br.readLine());
        System.out.println("Curso:");
        String nome_curso = br.readLine();
        Curso c = encontra_curso(ensino, nome_curso);

        a.setCurso(c);
        System.out.println("Matricula:");
        a.setMatricula(Long.parseLong(br.readLine()));
        System.out.println("Ingresso:");
        a.setAnoIngresso(Integer.parseInt(br.readLine()));
        a.setEhFormado(false);
        return a;
    }

    ////////////////// PROFESSOR ///////////////////////////////////////////
    private static void menu_professor(String opcoes, int posicao_professor, SetorEnsino ensino, BufferedReader br) throws IOException {
        int opcao = menu(opcoes, br);

        switch (opcao) {
            case OP_PROFESSOR_ADICIONAR_AREA:
                System.out.println("Qual a nova área, professor?");
                String area = br.readLine();

                if (nova_area(posicao_professor, ensino, area)) {
                    System.out.println("Área " + area + " cadastrada para o professor " + ensino.getProfessores().get(posicao_professor).toString());
                } else {
                    System.err.println("O limite de áreas foi atingido para o professor com siape " + ensino.getProfessores().get(posicao_professor).getSiape());
                }
                break;
            case OP_PROFESSOR_ALTERAR_NOTA:
                System.out.println("Qual a disciplina?");
                String disciplina = br.readLine();
                System.out.println("Qual o curso?");
                String curso = br.readLine();
                System.out.println("Qual o nome do aluno?");
                String nome_aluno = br.readLine();
                System.out.println("Qual a sua nova nota?");
                float nova_nota = Float.parseFloat(br.readLine());

                if (alterar_nota(ensino, disciplina, curso, nome_aluno, nova_nota)) {
                    System.out.println("Nota "
                            + nova_nota
                            + " alterada para o aluno "
                            + nome_aluno
                            + " na disciplina "
                            + disciplina
                            + " do curso "
                            + curso
                            + ".");
                } else {
                    System.err.println("Aluno "
                            + nome_aluno
                            + " do curso "
                            + curso
                            + " não foi encontrado. Ele não está matriculado na disciplina "
                            + disciplina);
                }
                break;
            case OP_PROFESSOR_DAR_NOTAS:
                System.out.println("Qual a disciplina?");
                disciplina = br.readLine();
                System.out.println("Qual o curso?");
                curso = br.readLine();
                dar_notas(ensino, disciplina, curso, br);
                break;
            case OP_PROFESSOR_REMOVER_AREA:
                System.out.println("Qual a área a ser removida, professor?");
                area = br.readLine();

                if (remover_area(posicao_professor, ensino, area)) {
                    System.out.println("Área " + area + " foi removido com sucesso para o professor " + ensino.getProfessores().get(posicao_professor).toString());
                } else {
                    System.err.println("A área " + area + " não estava cadastrada para o professor com siape " + ensino.getProfessores().get(posicao_professor).getSiape());
                }
                break;
        }
    }

    private static Professor cadastra_professor(BufferedReader br, SetorEnsino ensino) throws IOException {
        Professor p;

        p = cria_professor(br);
        if (ensino.novoProfessor(p)) {
            System.out.println("Professor " + p.toString() + " cadastrado.");
        } else {
            System.err.println("O limite de professores foi alcançado.");
        }
        return p;
    }

    private static Professor encontra_professor(SetorEnsino ensino, String nome_professor) {
        if (ensino.getProfessores() != null) {
            for (Professor p : ensino.getProfessores()) {
                if (p != null && p.getNome().equals(nome_professor)) {
                    return p;
                }
            }
        }
        return null;
    }

    private static Professor cria_professor(BufferedReader br) throws IOException {
        Professor p = new Professor();
        System.out.println("Nome:");
        p.setNome(br.readLine());
        System.out.println("SIAPE:");
        p.setSiape(Long.parseLong(br.readLine()));
        System.out.println("Quantas áreas?");
        int quantAreas = Integer.parseInt(br.readLine());

        p.setAreas(new ArrayList());
        System.out.println("Informe as áreas:");
        for (int i = 0; i < quantAreas; i++) {
            if (p.getAreas() != null && p.getAreas().get(i) != null) {
                p.getAreas().set(i, br.readLine());
            }
        }
        return p;
    }

    private static int login_professor(int siape, SetorEnsino ensino, BufferedReader br) {
        for (int i = 0; ensino.getProfessores() != null
                && i < ensino.getProfessores().size(); i++) {
            if (ensino.getProfessores().get(i) != null
                    && ensino.getProfessores().get(i).getSiape() == siape) {
                return i;
            }
        }
        return POSICAO_INVALIDA;
    }

    private static boolean nova_area(int pos_professor, SetorEnsino ensino, String area) {
        if (ensino.getProfessores() != null) {
            ArrayList<String> areas = ensino.getProfessores().get(pos_professor).getAreas();

            for (int i = 0; areas != null && i < areas.size(); i++) {
                if (areas.get(i) == null) {
                    areas.set(i, area);
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean remover_area(int pos_professor, SetorEnsino ensino, String area) {
        if (ensino.getProfessores() != null) {
            ArrayList<String> areas = ensino.getProfessores().get(pos_professor).getAreas();

            for (int i = 0; areas != null && i < areas.size(); i++) {
                if (areas.get(i).equals(area)) {
                    areas.set(i, null);
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean alterar_nota(SetorEnsino ensino, String nome_disciplina, String nome_curso, String nome_aluno, float nova_nota) {
        return ensino.alterarNota(nome_disciplina, nome_curso, nome_aluno, nova_nota);
    }

    private static void dar_notas(SetorEnsino ensino, String disciplina, String nome_curso, BufferedReader br) throws IOException {
        Curso curso = encontra_curso(ensino, nome_curso);

        if (curso == null) {
            System.err.println("Curso " + nome_curso + " não encontrado.");
        } else {
            Disciplina d = encontra_disciplina(curso, disciplina);

            if (d == null) {
                System.err.println("Disciplina " + disciplina + " não encontrada.");
            } else {

                System.out.println("Informe as notas dos alunos: ");
                ArrayList<Float> notas = new ArrayList();
                int i = 0;

                while (i < d.getAlunos().size()) {
                    System.out.println("Nota do aluno " + d.getAlunos().get(i).toString());
                    notas.set(i, Float.parseFloat(br.readLine()));
                    i++;
                }
                ensino.salvaNotas(notas, curso, d);
            }
        }
    }

    ///////////////////// Disciplinas //////////////////////////////////////////
    private static boolean cadastra_disciplina(SetorEnsino ensino, Curso c, Professor p, BufferedReader br) throws IOException {
        Disciplina d = cria_disciplina(br, p);

        return ensino.novaDisciplina(d, c);
    }

    private static Disciplina cria_disciplina(BufferedReader br, Professor p) throws IOException, NumberFormatException {
        System.out.println("Quantos alunos tem na turma?");
        int quantAlunos = Integer.parseInt(br.readLine());

        System.out.println("Qual o nome da disciplina?");
        String nome_disciplina = br.readLine();

        System.out.println("Qual o ano/semestre da disciplina?");
        int ano = Integer.parseInt(br.readLine());
        Disciplina d = new Disciplina(quantAlunos, p, nome_disciplina, ano);

        return d;
    }

    private static ArrayList<Disciplina> recebe_disciplinas(SetorEnsino ensino, BufferedReader br) throws IOException {
        ArrayList<Disciplina> disciplinas = new ArrayList();

        System.out.println("Digite ["
                + OP_DISCIPLINA_SAIR
                + "] para terminar e ["
                + OP_DISCIPLINA_NOVA
                + "] para cadastrar disciplina");
        int op = Integer.parseInt(br.readLine());

        for (int i = 0; op != OP_DISCIPLINA_SAIR && i < disciplinas.size(); i++) {
            System.out.println("Qual o nome do professor da disciplina?");
            String nome_professor = br.readLine();
            Professor professor = encontra_professor(ensino, nome_professor);

            if (professor == null) {
                System.err.println("O professor ainda não foi cadastrado. Informe seus dados.");
                professor = cria_professor(br);
                ensino.novoProfessor(professor);
            }
            disciplinas.set(i, cria_disciplina(br, professor));
            System.out.println("\n Digite [1] para terminar e [2] para cadastrar disciplina");
            op = Integer.parseInt(br.readLine());
        }
        return disciplinas;
    }

    private static void cadastra_disciplinas_aluno(BufferedReader br, SetorEnsino ensino, Aluno a) throws IOException {
        ensino.matricularAluno(a);
    }

    private static Disciplina encontra_disciplina(Curso curso, String disciplina) throws IOException {
        if (curso.getDisciplinas() != null) {
            for (Disciplina c : curso.getDisciplinas()) {
                if (c != null && c.getNome().equals(disciplina)) {
                    return c;
                }
            }
        }
        return null;
    }

    /////////////////////////// Curso //////////////////////////////////////////
    private static Curso cadastra_curso(SetorEnsino ensino, BufferedReader br) throws IOException {
        Curso c;

        c = cria_curso(ensino, br);
        if (ensino.novoCurso(c)) {
            System.out.println("Curso " + c.toString());
        } else {
            System.out.println("O limite de cursos foi alcançado.");
        }
        return c;
    }

    private static Curso cria_curso(SetorEnsino ensino, BufferedReader br) throws IOException {
        Curso a = new Curso();

        System.out.println("Nome:");
        a.setNome(br.readLine());
        System.out.println("PPC:");
        a.setPpc(br.readLine());
        ArrayList<Disciplina> disciplinas = recebe_disciplinas(ensino, br);

        a.setDisciplinas(disciplinas);
        return a;
    }

    private static Curso encontra_curso(SetorEnsino ensino, String nome) throws IOException {
        if (ensino.getCursos() != null) {
            for (Curso curso : ensino.getCursos()) {
                if (curso != null && curso.getNome().equals(nome)) {
                    return curso;
                }
            }
        }
        return null;
    }

    private static void ver_cursos(SetorEnsino ensino) {
        ArrayList<Curso> cursos = ensino.getCursos();

        if (cursos != null) {
            for (Curso curso : cursos) {
                if (curso != null) {
                    System.out.println(curso.toString());
                }
            }
        }
    }
}
