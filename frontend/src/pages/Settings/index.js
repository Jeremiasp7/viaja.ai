import React, { useEffect, useState } from "react";
import { Context } from "../../context/AuthContext";
import { useNavigate, useLocation } from "react-router-dom";
import { FaHome, FaSuitcase, FaHeart, FaCog } from "react-icons/fa";
import {
  getUserPreferences,
  updateUserPreferences,
} from "../../services/userPreferences";
import { getSuggestionByPreferences } from "../../services/sugestaoRoteiros";
import "./index.css";

export default function Settings() {
  const { user, authenticated } = React.useContext(Context);
  const userId = user?.id;

  const navigate = useNavigate();
  const location = useLocation();
  const path = location.pathname || "/";
  const isActive = (p) => path === p || path.startsWith(p + "/");

  const [form, setForm] = useState({
    estiloDeViagem: "",
    preferenciaDeAcomodacao: "",
    preferenciaDeClima: "",
    faixaOrcamentaria: "",
    duracaoDaViagem: "",
    companhiaDeViagem: "",
    preferenciaDeDatas: [],
  });
  const [dateInput, setDateInput] = useState("");
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);
  const [suggestion, setSuggestion] = useState("");
  const [error, setError] = useState(null);

  useEffect(() => {
    async function fetch() {
      if (!authenticated || !userId) return;
      try {
        const resp = await getUserPreferences(userId);
        if (resp.data) {
          // Map backend fields to form keys (they should match)
          setForm({
            estiloDeViagem: resp.data.estiloDeViagem || "",
            preferenciaDeAcomodacao: resp.data.preferenciaDeAcomodacao || "",
            preferenciaDeClima: resp.data.preferenciaDeClima || "",
            faixaOrcamentaria: resp.data.faixaOrcamentaria || "",
            duracaoDaViagem: resp.data.duracaoDaViagem || "",
            companhiaDeViagem: resp.data.companhiaDeViagem || "",
            preferenciaDeDatas: resp.data.preferenciaDeDatas || [],
          });
        }
      } catch (err) {
        // silent: user may not have preferences yet
        // console.warn('No preferences found', err);
      }
    }
    fetch();
  }, [authenticated, userId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((f) => ({ ...f, [name]: value }));
  };

  const addDate = () => {
    if (!dateInput) return;
    setForm((f) => ({
      ...f,
      preferenciaDeDatas: [...f.preferenciaDeDatas, dateInput],
    }));
    setDateInput("");
  };

  const removeDate = (idx) => {
    setForm((f) => ({
      ...f,
      preferenciaDeDatas: f.preferenciaDeDatas.filter((_, i) => i !== idx),
    }));
  };

  const handleSave = async (e) => {
    e.preventDefault();
    if (!userId) return setError("Usuário não autenticado");
    setSaving(true);
    setError(null);
    try {
      const payload = {
        ...form,
        duracaoDaViagem: form.duracaoDaViagem
          ? Number(form.duracaoDaViagem)
          : null,
      };
      await updateUserPreferences(userId, payload);
      setError(null);
      alert("Preferências salvas com sucesso");
    } catch (err) {
      console.error("Erro ao salvar preferências", err);
      setError(
        err.response?.data || err.message || "Erro ao salvar preferências",
      );
    } finally {
      setSaving(false);
    }
  };

  const handleGenerateSuggestion = async () => {
    if (!userId) return setError("Usuário não autenticado");
    setLoading(true);
    setSuggestion("");
    setError(null);
    try {
      const resp = await getSuggestionByPreferences(userId);
      setSuggestion(resp.data || String(resp));
    } catch (err) {
      console.error("Erro ao gerar sugestão por preferências", err);
      setError(err.response?.data || err.message || "Erro ao gerar sugestão");
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <div className="settings-page container">
        <h2>Preferências</h2>
        <div className="settings-grid">
          <form className="prefs-form" onSubmit={handleSave}>
            <label>
              Estilo de Viagem
              <input
                name="estiloDeViagem"
                value={form.estiloDeViagem}
                onChange={handleChange}
                placeholder="Ex: Aventura, Relax"
              />
            </label>

            <label>
              Preferência de Acomodação
              <input
                name="preferenciaDeAcomodacao"
                value={form.preferenciaDeAcomodacao}
                onChange={handleChange}
                placeholder="Ex: Hotel, Hostel, Airbnb"
              />
            </label>

            <label>
              Preferência de Clima
              <input
                name="preferenciaDeClima"
                value={form.preferenciaDeClima}
                onChange={handleChange}
                placeholder="Ex: Quente, Frio"
              />
            </label>

            <label>
              Faixa Orçamentária
              <input
                name="faixaOrcamentaria"
                value={form.faixaOrcamentaria}
                onChange={handleChange}
                placeholder="Ex: baixo, médio, alto"
              />
            </label>

            <label>
              Duração da Viagem (dias)
              <input
                name="duracaoDaViagem"
                type="number"
                min="1"
                value={form.duracaoDaViagem}
                onChange={handleChange}
              />
            </label>

            <label>
              Companhia de Viagem
              <input
                name="companhiaDeViagem"
                value={form.companhiaDeViagem}
                onChange={handleChange}
                placeholder="Ex: sozinho, casal, família"
              />
            </label>

            <div className="dates-block">
              <label>Preferência de Datas</label>
              <div className="dates-input-row">
                <input
                  type="date"
                  value={dateInput}
                  onChange={(e) => setDateInput(e.target.value)}
                />
                <button type="button" onClick={addDate}>
                  Adicionar
                </button>
              </div>
              <ul className="dates-list">
                {form.preferenciaDeDatas.map((d, i) => (
                  <li key={i}>
                    {d}{" "}
                    <button type="button" onClick={() => removeDate(i)}>
                      Remover
                    </button>
                  </li>
                ))}
              </ul>
            </div>

            {error && <div className="error">{String(error)}</div>}
            <div className="form-actions">
              <button type="submit" disabled={saving}>
                {saving ? "Salvando..." : "Salvar Preferências"}
              </button>
            </div>
          </form>

          <aside className="prefs-suggestion">
            <h3>Sugestão de Roteiro (por Preferências)</h3>
            <p>Gere uma sugestão baseada nas suas preferências salvas.</p>
            <div className="suggestion-actions">
              <button
                className="buttonSubmit"
                onClick={handleGenerateSuggestion}
                disabled={loading}
              >
                {loading ? "Gerando..." : "Gerar sugestão para mim"}
              </button>
            </div>
            <div className="suggestion-result">
              {suggestion ? (
                <pre className="result-pre">{suggestion}</pre>
              ) : (
                <div className="hint">Nenhuma sugestão gerada ainda.</div>
              )}
            </div>
          </aside>
        </div>
      </div>

      {/* duplicate footer here so it appears on the Ajustes/Preferências screen as well */}
      <footer className="app-footer">
        <div
          className={`footer-icon ${isActive("/dashboard") ? "active" : ""}`}
          onClick={() => navigate("/dashboard")}
        >
          <FaHome />
          <p>Início</p>
        </div>
        <div
          className={`footer-icon ${isActive("/itinerary") ? "active" : ""}`}
          onClick={() => navigate("/dashboard")}
        >
          <FaSuitcase />
          <p>Roteiros</p>
        </div>
        <div
          className={`footer-icon ${isActive("/favoritos") ? "active" : ""}`}
        >
          <FaHeart />
          <p>Favoritos</p>
        </div>
        <div
          className={`footer-icon ${isActive("/ajustes") ? "active" : ""}`}
          onClick={() => navigate("/ajustes")}
          title="Preferências"
        >
          <FaCog />
          <p>Preferências</p>
        </div>
      </footer>
    </>
  );
}
