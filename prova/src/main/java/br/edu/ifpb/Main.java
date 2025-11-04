package br.edu.ifpb;

import br.edu.ifpb.dao.AlunoDao;
import br.edu.ifpb.dao.DaoFactory;
import br.edu.ifpb.dao.DisciplinaDao;
import br.edu.ifpb.dao.ProfessorDao;
import br.edu.ifpb.model.Aluno;
import br.edu.ifpb.model.Disciplina;
import br.edu.ifpb.model.Professor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("crud-pu");
        final EntityManager em = emf.createEntityManager();
        AlunoDao alunoDao = DaoFactory.criarAlunoDao(em);
        DisciplinaDao disciplinaDao = DaoFactory.criarDisciplinaDao(em);
        ProfessorDao professorDao = DaoFactory.criarProfessorDao(em);
        int opcao = 0;
        do {
            System.out.println("""
                    1) Cadastrar Aluno (20 pts)
                    2) Cadastrar Disciplina (30 pts)
                    3) Adicionar Aluno na Disciplina (30 pts)
                    4) Listar Alunos por Disciplina (20 pts)
                    0) Sair
                    Escolha uma opção: 
                    """);
            opcao = new Scanner(System.in).nextInt();
            switch (opcao) {
                 case 1 -> {
                     em.getTransaction().begin();
                     System.out.println("Nome: ");
                     String nome = new Scanner(System.in).nextLine();
                     System.out.println("Matrícula: ");
                     String matricula = new Scanner(System.in).nextLine();
                     Aluno aluno = new Aluno(nome, matricula);
                     alunoDao.salvar(aluno);
                     System.out.println("Aluno cadastrado com sucesso!");
                     em.getTransaction().commit();
                     break;
                 }
                 case 2 -> {
                     em.getTransaction().begin();
                     System.out.println("Nome da Disciplina: ");
                     String nomeDisciplina = new Scanner(System.in).nextLine();
                     System.out.println("Carga horária: ");
                     Integer ch = new Scanner(System.in).nextInt();
                     System.out.println("Nome do Professor: ");
                     String nomeProfessor = new Scanner(System.in).nextLine();
                     System.out.println("CPF do Professor: ");
                     String cpfProfessor = new Scanner(System.in).nextLine();
                     System.out.println("Titulação do Professor: ");
                     String titulacaoProfessor = new Scanner(System.in).nextLine();
                     Professor professor = new Professor(nomeProfessor, cpfProfessor, titulacaoProfessor);
                     professorDao.salvar(professor);
                     Disciplina disciplina = new Disciplina(nomeDisciplina, ch, professor);
                     disciplinaDao.salvar(disciplina);
                     em.getTransaction().commit();
                     break;
                 }
                 case 3 -> {
                     em.getTransaction().begin();
                     Set<Disciplina> disciplinas = disciplinaDao.listarTodas();
                     disciplinas.forEach(d ->System.out.printf("ID: %d - Nome: %sn%n", d.getId(), d.getNome()));
                     System.out.print("Digite o ID da Disciplina: ");
                     Long disciplinaId = new Scanner(System.in).nextLong();
                     Disciplina disciplina = disciplinaDao.buscarPorId(disciplinaId);
                     if (disciplina == null) {
                         System.out.println("Disciplina não encontrada!");
                         em.getTransaction().rollback();
                         break;
                     }
                     System.out.print("Digite o ID do Aluno: ");
                     Long idAluno = new Scanner(System.in).nextLong();
                     Aluno aluno = alunoDao.buscarPorId(idAluno);
                     if (aluno == null) {
                        System.out.println("Aluno não encontrado!");
                        em.getTransaction().rollback();
                        break;
                     }
                     aluno.getDisciplinas().add(disciplina);
                     alunoDao.salvar(aluno);
                     em.getTransaction().commit();
                     System.out.println("Aluno adicionado na disciplina com sucesso!");
                     break;
                 }
                 case 4 -> {
                     Set<Disciplina> disciplinas = disciplinaDao.listarTodas();
                     disciplinas.forEach(d ->System.out.printf("ID: %d - Nome: %sn", d.getId(), d.getNome()));
                     System.out.print("Digite o ID da Disciplina: ");
                     Long disciplinaId = new Scanner(System.in).nextLong();
                     Disciplina disciplina = disciplinaDao.buscarPorId(disciplinaId);
                     if (disciplina == null) {
                         System.out.println("Disciplina não encontrada!");
                         break;
                     }
                     Set<Aluno> alunos = alunoDao.buscarPorDisciplina(disciplinaId);
                     alunos.forEach(a -> System.out.printf("ID: %d - Nome: %s - Matrícula: %s%n", a.getId(), a.getNome(), a.getMatricula()));
                     break;
                 }
                 case 0 -> System.out.println("Saindo...");
                 default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
}