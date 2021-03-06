package ProjetoAcademico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 *
 * @author coelho
 */
public class Disciplina implements Serializable, Comparable<Disciplina> {

    public static final int MAX_ALUNOS = 30;

    private ArrayList<Aluno> alunos;
    private Professor professor;
    private String nome;
    private int ano;
    private HashMap<Long, Float> notas;

    public Disciplina() {
        alunos = new ArrayList();
        notas = new HashMap();
    }

    public boolean registrarNota(float nota, String nome) {
        for (int i = 0; i < notas.size(); i++) {
            if (alunos.get(i).getNome().equals(nome)) {
                notas.put(alunos.get(i).getMatricula(), nota);
                return true;
            }
        }
        return false;
    }

    public boolean registrarAluno(String nome, Curso curso, int anoIngresso, long matricula) {
        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i) != null) {
                alunos.set(i, new Aluno(nome, curso, anoIngresso, matricula));
                return true;
            }
        }
        return false;
    }

    public boolean alterarNota(float nota, String nome) {
        return registrarNota(nota, nome);
    }

    public boolean removerAluno(String nome) {
        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i).getNome().equals(nome)) {
                alunos.set(i, null);
                return true;
            }
        }
        return false;
    }

    public void registrarAluno(Aluno a) {
        registrarAluno(a.getNome(), a.getCurso(), a.getAnoIngresso(), a.getMatricula());
    }

    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public HashMap<Long, Float> getNotas() {
        return notas;
    }

    public void setNotas(HashMap<Long, Float> notas) {
        this.notas = notas;
    }

    public Disciplina(Professor professor, String nome, int ano) {
        this.professor = professor;
        this.nome = nome;
        this.ano = ano;
        this.alunos = new ArrayList();
        this.notas = new HashMap();
    }

    public Disciplina(int quantAlunos, Professor professor, String nome, int ano) {
        this.alunos = new ArrayList();
        this.notas = new HashMap();
        this.professor = professor;
        this.nome = nome;
        this.ano = ano;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj != null
                && getClass() == obj.getClass()) {
            final Disciplina other = (Disciplina) obj;

            if (this.professor.equals(other.professor)
                    && this.nome.equals(other.nome)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.professor);
        hash = 97 * hash + Objects.hashCode(this.nome);
        return hash;
    }

    @Override
    public String toString() {
        String notas_alunos = "";

        for (int i = 0; i < alunos.size(); i++) {
            Aluno aluno = alunos.get(i);
            float nota = notas.get(i);

            notas_alunos += aluno.toString() + " Nota: " + nota + "\n";
        }
        return "\n professor: " + professor
                + "\n nome: " + nome
                + "\n ano: " + ano
                + "\n Notas: " + notas_alunos;
    }
    
    @Override
    public int compareTo(Disciplina other) {
        return this.nome.compareTo(other.nome);
    }
}
