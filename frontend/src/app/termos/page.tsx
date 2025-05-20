"use client";

import "./page_termos.css";
import Link from 'next/link';

export default function TermosDeUso() {
  return (
    <div className="global-termos">
      <div className="container-termos">
        <h1 className="titulo">Termos de Uso</h1>
        <p className="subtitulo">Leia atentamente os termos e condições de uso do Midas</p>

        <div className="termos-content">
          <section className="termos-section">
            <h2>1. Aceitação dos Termos</h2>
            <p>
              Ao acessar e utilizar o sistema Midas, você concorda em cumprir e estar vinculado a estes Termos de Uso. 
              Se você não concordar com qualquer parte destes termos, não poderá acessar ou utilizar nossos serviços.
            </p>
          </section>

          <section className="termos-section">
            <h2>2. Descrição do Serviço</h2>
            <p>
              O Midas é uma plataforma de gestão financeira que permite aos usuários controlar suas finanças pessoais, 
              registrar receitas e despesas, criar orçamentos e visualizar relatórios financeiros.
            </p>
          </section>

          <section className="termos-section">
            <h2>3. Cadastro e Segurança</h2>
            <p>
              Para utilizar o Midas, é necessário criar uma conta com informações precisas e completas. Você é responsável 
              por manter a confidencialidade de sua senha e por todas as atividades que ocorrerem em sua conta.
            </p>
            <p>
              Você concorda em notificar imediatamente o Midas sobre qualquer uso não autorizado de sua conta ou qualquer 
              outra violação de segurança.
            </p>
          </section>

          <section className="termos-section">
            <h2>4. Privacidade e Proteção de Dados</h2>
            <p>
              Respeitamos sua privacidade e estamos comprometidos em proteger seus dados pessoais. Nossas práticas de 
              privacidade estão descritas em nossa Política de Privacidade, que faz parte integrante destes Termos de Uso.
            </p>
            <p>
              Ao utilizar o Midas, você concorda com a coleta, uso e processamento de suas informações conforme descrito 
              em nossa Política de Privacidade.
            </p>
          </section>

          <section className="termos-section">
            <h2>5. Limitações de Responsabilidade</h2>
            <p>
              O Midas não será responsável por quaisquer danos diretos, indiretos, incidentais, especiais ou consequentes 
              resultantes do uso ou da incapacidade de usar nossos serviços.
            </p>
            <p>
              As informações financeiras fornecidas pelo Midas são apenas para fins informativos e não constituem 
              aconselhamento financeiro profissional.
            </p>
          </section>

          <section className="termos-section">
            <h2>6. Alterações nos Termos</h2>
            <p>
              O Midas reserva-se o direito de modificar estes Termos de Uso a qualquer momento. As alterações entrarão 
              em vigor imediatamente após a publicação dos termos atualizados.
            </p>
            <p>
              O uso continuado do Midas após a publicação de quaisquer alterações constitui aceitação dessas alterações.
            </p>
          </section>

          <section className="termos-section">
            <h2>7. Lei Aplicável</h2>
            <p>
              Estes Termos de Uso serão regidos e interpretados de acordo com as leis do Brasil, independentemente 
              de conflitos de disposições legais.
            </p>
          </section>
        </div>

        <div className="termos-actions">
          <Link href="/cadastro">
            <button className="termos-button">Voltar</button>
          </Link>
        </div>
      </div>
    </div>
  );
}