/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjetoAcademico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author coelho
 */
public class SetorEnsino implements Serializable {

    public static final int MAX_PROFESSORES = 60;
    public static final int MAX_CURSOS = 12;
    
    private ArrayList<Curso> cursos;
    private ArrayList<Professor> professores;
    private String diretor;
    private String coordenador;

    public boolean novoProfessor(String nome, long siape) {
        for (int i = 0; i < professores.size(); i++) {
            if (professores.get(i) != null) {
                professores.set(i, new Professor(nome, siape));
                return true;
            }
        }
        return false;
    }

    public boolean novoProfessor(String nome, long siape, ArrayList<String> areas) {
        for (int i = 0; i < professores.size(); i++) {
            if (professores.get(i) != null) {
                professores.set(i, new Professor(nome, siape, areas));
                return true;
            }
        }
        return false;
    }

    public boolean novoProfessor(Professor p) {
        for (int i = 0; i < professores.size(); i++) {
            if (professores.get(i) != null) {
                professores.set(i, p);
                return true;
            }
        }
        return false;
    }

    public boolean demitirProfessor(long siape) {
        for (int i = 0; i < professores.size(); i++) {
            if (professores.get(i) != null && professores.get(i).getSiape() == siape) {
                professores.set(i, null);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(ArrayList<Curso> cursos) {
        this.cursos = cursos;
    }

    public ArrayList<Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(ArrayList<Professor> professores) {
        this.professores = professores;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(String coordenador) {
        this.coordenador = coordenador;
    }

    public SetorEnsino(ArrayList<Curso> cursos, ArrayList<Professor> professores, String diretor, String coordenador) {
        this.cursos = cursos;
        this.professores = professores;
        this.diretor = diretor;
        this.coordenador = coordenador;
        this.professores = new ArrayList();

    }

    public SetorEnsino(String diretor, String coordenador) {
        this.diretor = diretor;
        this.coordenador = coordenador;
        this.professores = new ArrayList();
        this.cursos = new ArrayList();
    }

    public void matricularAluno(Aluno a) {
        for (Curso curso : cursos) {
            if (curso.getNome().equals(a.getCurso().getNome())) {
                for (Disciplina disciplina : curso.getDisciplinas()) {
                    a.setCurso(curso);
                    disciplina.registrarAluno(a);
                }
            }
        }
    }

    public boolean alterarNota(String nome_disciplina, String nome_curso, String nome_aluno, float nova_nota) {
        for (Curso curso : cursos) {
            if (curso.getNome().equals(nome_curso)) {
                for (Disciplina disciplina : curso.getDisciplinas()) {
                    if (disciplina.getNome().equals(nome_disciplina)) {
                        ArrayList<Aluno> alunos = disciplina.getAlunos();

                        for (int i = 0; i < alunos.size(); i++) {
                            if (alunos.get(i).getNome().equals(nome_aluno)) {
                                disciplina.getNotas().set(i, nova_nota);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void salvaNotas(ArrayList<Float> notas,
            Curso curso,
            Disciplina disciplina) {
        for (Curso c : cursos) {
            if (c.equals(curso)) {
                for (Disciplina d : c.getDisciplinas()) {
                    if (d.equals(disciplina)) {
                        d.setNotas(notas);
                    }
                }
            }
        }
    }

    public boolean novaDisciplina(Disciplina disciplina, Curso curso) {
        for (Curso c : cursos) {
            if (c.equals(curso)) {
                for (int i = 0; i < c.getDisciplinas().size(); i++) {
                    if (c.getDisciplinas().get(i) == null) {
                        c.getDisciplinas().add(disciplina);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean novoCurso(Curso c) {
        for (int i = 0; i < getCursos().size(); i++) {
            if (getCursos().get(i) == null) {
                getCursos().set(i, c);
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.cursos);
        hash = 61 * hash + Objects.hashCode(this.professores);
        hash = 61 * hash + Objects.hashCode(this.diretor);
        hash = 61 * hash + Objects.hashCode(this.coordenador);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SetorEnsino other = (SetorEnsino) obj;
        if (!Objects.equals(this.diretor, other.diretor)) {
            return false;
        }
        return (Objects.equals(this.coordenador, other.coordenador));
    }

    @Override
    public String toString() {
        String nomes_cursos = "";

        for (Curso d : cursos) {
            nomes_cursos += d.toString() + "\n";
        }
        String nomes_professores = "";

        for (Professor e : professores) {
            nomes_professores += e.toString() + "\n";
        }
        return "\n cursos: " 
                + nomes_cursos 
                + "\n professores: " 
                + nomes_professores 
                + "\n diretor: " 
                + diretor 
                + "\n coordenador: " + coordenador;
    }

    
    
}
