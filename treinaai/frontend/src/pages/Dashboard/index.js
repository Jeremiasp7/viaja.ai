import React, { useContext, useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { Context } from "../../context/AuthContext";
import api from "../../api";
import "./index.css";
import { FiLogOut, FiPlus } from "react-icons/fi";

function Dashboard() {
  const { user, userId, handleLogout } = useContext(Context);
  const { register, handleSubmit, reset, formState: { errors } } = useForm();
  const [loading, setLoading] = useState(false);
  const [serverError, setServerError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [plans, setPlans] = useState([]);

  useEffect(() => {
    async function fetchPlans() {
      const id = user?.id || userId;
      if (!id) return;
      try {
        // Usa a rota específica de planos de treino
        const resp = await api.get(`/api/train-plans/user/${id}`);
        setPlans(resp.data || []);
      } catch (e) {
        console.warn('Erro ao carregar planos', e);
      }
    }
    fetchPlans();
  }, [user]);

  const onSubmit = async (data) => {
    setLoading(true);
    setServerError("");
    setSuccessMessage("");

    try {
      const payload = {
        titulo: data.titulo,
        descricao: data.descricao,
        dataInicio: data.dataInicio,
        dataTermino: data.dataTermino,
        diasTreino: parseInt(data.diasTreino),
        duracao: parseInt(data.duracao),
        atividades: data.atividades.split("\n").filter(a => a.trim()),
      };

      // Garante que a rota correta seja usada para criação de plano
      const id = user?.id || userId;
      if (!id) {
        throw new Error("Usuário inválido para criação do plano.");
      }
      await api.post(`/api/train-plans/${id}`, payload);
      setSuccessMessage("Plano de treino criado com sucesso!");
      reset();
      setShowForm(false);
      // Recarrega lista de planos do usuário
      try {
        const resp = await api.get(`/api/train-plans/user/${id}`);
        setPlans(resp.data || []);
      } catch {}

      setTimeout(() => setSuccessMessage(""), 3000);
    } catch (error) {
      const serverMsg = error?.response?.data?.message || error?.response?.data?.error || error?.message;
      setServerError(serverMsg || "Erro ao criar plano de treino. Tente novamente.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <div className="header-content">
          <h1>TreinaAI</h1>
          <div className="header-info">
            <span className="user-name">Olá, {user?.nome || user?.name || user?.email || "Usuário"}!</span>
            <button
              className="btn btn-logout"
              onClick={handleLogout}
              title="Sair"
            >
              <FiLogOut /> Sair
            </button>
          </div>
        </div>
      </header>

      <main className="dashboard-content">
        <div className="dashboard-container">
          <div className="dashboard-welcome">
            <h2>Bem-vindo ao seu Espaço de Treinos</h2>
            <p>Crie e gerencie seus planos de treino personalizados</p>
          </div>

          {successMessage && (
            <div className="alert alert-success">{successMessage}</div>
          )}

          {serverError && (
            <div className="alert alert-error">{serverError}</div>
          )}

          <div className="form-section">
            {!showForm ? (
              <button
                className="btn btn-create"
                onClick={() => setShowForm(true)}
              >
                <FiPlus /> Criar Novo Plano de Treino
              </button>
            ) : (
              <form onSubmit={handleSubmit(onSubmit)} className="train-plan-form">
                <h3>Criar Novo Plano de Treino</h3>

                <div className="form-row">
                  <div className="form-group">
                    <label htmlFor="titulo">Título do Plano</label>
                    <input
                      type="text"
                      id="titulo"
                      placeholder="Ex: Treino para Corrida"
                      {...register("titulo", {
                        required: "Título é obrigatório",
                      })}
                    />
                    {errors.titulo && (
                      <span className="error-message">{errors.titulo.message}</span>
                    )}
                  </div>

                  <div className="form-group">
                    <label htmlFor="descricao">Descrição</label>
                    <input
                      type="text"
                      id="descricao"
                      placeholder="Descreva seu plano"
                      {...register("descricao", {
                        required: "Descrição é obrigatória",
                      })}
                    />
                    {errors.descricao && (
                      <span className="error-message">{errors.descricao.message}</span>
                    )}
                  </div>
                </div>

                <div className="form-row">
                  <div className="form-group">
                    <label htmlFor="dataInicio">Data de Início</label>
                    <input
                      type="date"
                      id="dataInicio"
                      {...register("dataInicio", {
                        required: "Data de início é obrigatória",
                      })}
                    />
                    {errors.dataInicio && (
                      <span className="error-message">{errors.dataInicio.message}</span>
                    )}
                  </div>

                  <div className="form-group">
                    <label htmlFor="dataTermino">Data de Término</label>
                    <input
                      type="date"
                      id="dataTermino"
                      {...register("dataTermino", {
                        required: "Data de término é obrigatória",
                      })}
                    />
                    {errors.dataTermino && (
                      <span className="error-message">{errors.dataTermino.message}</span>
                    )}
                  </div>
                </div>

                <div className="form-row">
                  <div className="form-group">
                    <label htmlFor="diasTreino">Dias de Treino (por semana)</label>
                    <input
                      type="number"
                      id="diasTreino"
                      min="1"
                      max="7"
                      placeholder="Ex: 4"
                      {...register("diasTreino", {
                        required: "Dias de treino é obrigatório",
                        min: { value: 1, message: "Mínimo 1 dia" },
                        max: { value: 7, message: "Máximo 7 dias" },
                      })}
                    />
                    {errors.diasTreino && (
                      <span className="error-message">{errors.diasTreino.message}</span>
                    )}
                  </div>

                  <div className="form-group">
                    <label htmlFor="duracao">Duração de cada Treino (minutos)</label>
                    <input
                      type="number"
                      id="duracao"
                      min="15"
                      placeholder="Ex: 60"
                      {...register("duracao", {
                        required: "Duração é obrigatória",
                        min: { value: 15, message: "Mínimo 15 minutos" },
                      })}
                    />
                    {errors.duracao && (
                      <span className="error-message">{errors.duracao.message}</span>
                    )}
                  </div>
                </div>

                <div className="form-group">
                  <label htmlFor="atividades">Atividades (uma por linha)</label>
                  <textarea
                    id="atividades"
                    placeholder="Ex:&#10;Corrida leve 5km&#10;Corrida intervalada&#10;Treino de força"
                    rows="5"
                    {...register("atividades", {
                      required: "Atividades são obrigatórias",
                    })}
                  />
                  {errors.atividades && (
                    <span className="error-message">{errors.atividades.message}</span>
                  )}
                </div>

                <div className="form-actions">
                  <button
                    type="submit"
                    className="btn btn-primary"
                    disabled={loading}
                  >
                    {loading ? "Criando..." : "Criar Plano"}
                  </button>
                  <button
                    type="button"
                    className="btn btn-secondary"
                    onClick={() => {
                      setShowForm(false);
                      reset();
                    }}
                  >
                    Cancelar
                  </button>
                </div>
              </form>
            )}
          </div>

          <div className="plans-section">
            <h3>Seus Planos de Treino</h3>
            {plans.length === 0 ? (
              <p>Nenhum plano cadastrado ainda.</p>
            ) : (
              <ul className="plans-list">
                {plans.map((p) => (
                  <li key={p.id} className="plan-item">
                    <div className="plan-header">
                      <strong>{p.titulo || p.title}</strong>
                      <span>
                        {(p.dataInicio || p.startDate)} &rarr; {(p.dataTermino || p.endDate)}
                      </span>
                    </div>
                    <div className="plan-body">
                      <p>{p.descricao}</p>
                      <small>
                        {p.diasTreino} dias/semana • {p.duracao} min/treino
                      </small>
                      {(() => {
                        const atividades = p.atividades || p.dayActivities || [];
                        return Array.isArray(atividades) && atividades.length > 0 ? (
                          <ul className="atividades-list">
                            {atividades.map((a, idx) => (
                              <li key={idx}>{a}</li>
                            ))}
                          </ul>
                        ) : null;
                      })()}
                    </div>
                  </li>
                ))}
              </ul>
            )}
          </div>
        </div>
      </main>
    </div>
  );
}

export default Dashboard;
