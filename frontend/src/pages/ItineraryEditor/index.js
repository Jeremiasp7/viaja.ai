import React, { useState, useEffect, useContext } from "react";
import {
  FaArrowLeft,
  FaHotel,
  FaUtensils,
  FaLandmark,
  FaPlus,
  FaTrash,
} from "react-icons/fa";
import { useNavigate, useParams } from "react-router-dom";
import { getTravelPlanById } from "../../services/travelPlans";
import { Context } from "../../context/AuthContext";
import {
  getSuggestionByPreferences,
  getSuggestionByTravelPlan,
} from "../../services/sugestaoRoteiros";
import travelPlanService from "../../services/travelPlans";

import "./index.css";
import TravelMap from "../Maps";
import SugestaoDestino from "../SugestaoDestino/index.js";

const ItineraryManager = () => {
  const [activeTab, setActiveTab] = useState("manual");
  const [plan, setPlan] = useState(null);
  const [loading, setLoading] = useState(false);
  const [editingDates, setEditingDates] = useState(false);
  const [dateForm, setDateForm] = useState({ startDate: "", endDate: "" });
  const [showDestForm, setShowDestForm] = useState(false);
  const [destForm, setDestForm] = useState({
    country: "",
    city: "",
    arrivalDate: "",
    departureDate: "",
  });
  const [addingActivityFor, setAddingActivityFor] = useState(null); // index of day where adding
  const [newActivityText, setNewActivityText] = useState("");
  const [saving, setSaving] = useState(false);

  const navigate = useNavigate();
  const { id } = useParams();
  const { user, authenticated } = useContext(Context);

  // suggestion state
  const [sugLoading, setSugLoading] = useState(false);
  const [sugResult, setSugResult] = useState("");
  const [sugError, setSugError] = useState(null);

  useEffect(() => {
    async function loadPlan() {
      if (!id) return;
      setLoading(true);
      try {
        const resp = await getTravelPlanById(id);
        setPlan(resp.data);
        setDateForm({
          startDate: resp.data.startDate || "",
          endDate: resp.data.endDate || "",
        });
      } catch (err) {
        console.error("Erro ao carregar travel plan:", err);
      } finally {
        setLoading(false);
      }
    }
    loadPlan();
  }, [id]);

  function formatDate(iso) {
    if (!iso) return "";
    try {
      const d = new Date(iso);
      return d.toLocaleDateString("pt-BR", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
      });
    } catch (e) {
      return iso;
    }
  }

  function dateForDay(index) {
    if (!plan?.startDate) return "";
    try {
      const base = new Date(plan.startDate);
      const day = new Date(base);
      day.setDate(base.getDate() + index);
      return day.toLocaleDateString("pt-BR");
    } catch (e) {
      return "";
    }
  }

  return (
    <div className="planner-screen">
      <header className="planner-header">
        <button onClick={() => navigate("/dashboard")} className="back-button">
          <FaArrowLeft /> Voltar
        </button>
        <div style={{ display: "flex", flexDirection: "column", gap: 6 }}>
          <div style={{ display: "flex", alignItems: "center", gap: 12 }}>
            <h3 style={{ margin: 0 }}>
              Roteiro: {plan?.title || "Novo Roteiro"}
            </h3>
            {!editingDates && plan?.startDate && (
              <div className="plan-dates">
                {formatDate(plan.startDate)} — {formatDate(plan.endDate)}
              </div>
            )}
            {editingDates && (
              <div className="date-edit-inline">
                <input
                  type="date"
                  value={dateForm.startDate || ""}
                  onChange={(e) =>
                    setDateForm((f) => ({ ...f, startDate: e.target.value }))
                  }
                />
                <input
                  type="date"
                  value={dateForm.endDate || ""}
                  onChange={(e) =>
                    setDateForm((f) => ({ ...f, endDate: e.target.value }))
                  }
                />
                <button
                  className="btn-secondary"
                  onClick={() => setEditingDates(false)}
                >
                  Cancelar
                </button>
                <button
                  className="btn-create"
                  onClick={async () => {
                    if (!plan) return;
                    const updated = {
                      ...plan,
                      startDate: dateForm.startDate,
                      endDate: dateForm.endDate,
                    };
                    setSaving(true);
                    try {
                      await import("../../services/travelPlans").then((m) =>
                        m.updateTravelPlan(id, {
                          title: updated.title,
                          startDate: updated.startDate,
                          endDate: updated.endDate,
                          userId: updated.userId,
                          destinations: updated.destinations || [],
                          dayItinerary: updated.dayItinerary || [],
                        }),
                      );
                      setPlan(updated);
                      setEditingDates(false);
                    } catch (err) {
                      console.error("Erro ao salvar datas:", err);
                      alert("Erro ao salvar datas");
                    } finally {
                      setSaving(false);
                    }
                  }}
                >
                  {saving ? "Salvando..." : "Salvar"}
                </button>
              </div>
            )}
            {!editingDates && (
              <button
                className="btn-secondary"
                onClick={() => setEditingDates(true)}
              >
                Editar datas
              </button>
            )}
          </div>
        </div>
        <div className="tabs">
          <button
            className={activeTab === "manual" ? "active" : ""}
            onClick={() => setActiveTab("manual")}
          >
            Mapas
          </button>
          <button
            className={activeTab === "ia" ? "active" : ""}
            onClick={() => setActiveTab("ia")}
          >
            Sugestão da IA
          </button>
          <button
            className={activeTab === "iaDestino" ? "active" : ""}
            onClick={() => setActiveTab("iaDestino")}
          >
            Sugestão da IA
          </button>
        </div>
      </header>

      <div className="planner-content">
        <div className="itinerary-column">
          {/* Destinations block - must appear above dayItinerary */}
          {loading ? (
            <p>Carregando roteiro...</p>
          ) : (
            <>
              <div
                style={{
                  display: "flex",
                  justifyContent: "space-between",
                  alignItems: "center",
                }}
              >
                <h4>Destinos</h4>
                <div>
                  <button
                    className="btn-secondary"
                    onClick={() => setShowDestForm((s) => !s)}
                  >
                    {showDestForm ? "Cancelar" : "Adicionar destino"}
                  </button>
                </div>
              </div>
              {showDestForm && (
                <div className="dest-form">
                  <input
                    placeholder="País"
                    value={destForm.country}
                    onChange={(e) =>
                      setDestForm((d) => ({ ...d, country: e.target.value }))
                    }
                  />
                  <input
                    placeholder="Cidade"
                    value={destForm.city}
                    onChange={(e) =>
                      setDestForm((d) => ({ ...d, city: e.target.value }))
                    }
                  />
                  <input
                    type="date"
                    placeholder="Chegada"
                    value={destForm.arrivalDate}
                    onChange={(e) =>
                      setDestForm((d) => ({
                        ...d,
                        arrivalDate: e.target.value,
                      }))
                    }
                  />
                  <input
                    type="date"
                    placeholder="Saída"
                    value={destForm.departureDate}
                    onChange={(e) =>
                      setDestForm((d) => ({
                        ...d,
                        departureDate: e.target.value,
                      }))
                    }
                  />
                  <div style={{ display: "flex", gap: 8 }}>
                    <button
                      className="btn-create"
                      onClick={async () => {
                        if (!plan) return;
                        const newDest = {
                          country: destForm.country,
                          city: destForm.city,
                          arrivalDate: destForm.arrivalDate,
                          departureDate: destForm.departureDate,
                        };
                        const updated = {
                          ...plan,
                          destinations: [...(plan.destinations || []), newDest],
                        };
                        setSaving(true);
                        try {
                          await import("../../services/travelPlans").then((m) =>
                            m.updateTravelPlan(id, {
                              title: updated.title,
                              startDate: updated.startDate,
                              endDate: updated.endDate,
                              userId: updated.userId,
                              destinations: updated.destinations,
                              dayItinerary: updated.dayItinerary || [],
                            }),
                          );
                          setPlan(updated);
                          setDestForm({
                            country: "",
                            city: "",
                            arrivalDate: "",
                            departureDate: "",
                          });
                          setShowDestForm(false);
                        } catch (err) {
                          console.error("Erro ao adicionar destino:", err);
                          alert("Erro ao adicionar destino");
                        } finally {
                          setSaving(false);
                        }
                      }}
                    >
                      {saving ? "Adicionando..." : "Adicionar"}
                    </button>
                    <button
                      className="btn-secondary"
                      onClick={() => setShowDestForm(false)}
                    >
                      Cancelar
                    </button>
                  </div>
                </div>
              )}
              {plan?.destinations && plan.destinations.length > 0 && (
                <div className="destinations-list">
                  {plan.destinations.map((d, i) => (
                    <div className="destination-card" key={i}>
                      <div
                        style={{
                          display: "flex",
                          justifyContent: "space-between",
                          alignItems: "center",
                        }}
                      >
                        <div>
                          <div className="dest-title">
                            {d.city || ""}
                            {d.country ? `, ${d.country}` : ""}
                          </div>
                          <div className="dest-dates">
                            {d.arrivalDate ? formatDate(d.arrivalDate) : ""} —{" "}
                            {d.departureDate ? formatDate(d.departureDate) : ""}
                          </div>
                        </div>
                        <div>
                          <button
                            className="btn-icon"
                            title="Remover destino"
                            onClick={async () => {
                              if (!plan) return;
                              if (!window.confirm("Remover esse destino?"))
                                return;
                              const updated = {
                                ...plan,
                                destinations: plan.destinations.filter(
                                  (_, di) => di !== i,
                                ),
                              };
                              setSaving(true);
                              try {
                                await import("../../services/travelPlans").then(
                                  (m) =>
                                    m.updateTravelPlan(id, {
                                      title: updated.title,
                                      startDate: updated.startDate,
                                      endDate: updated.endDate,
                                      userId: updated.userId,
                                      destinations: updated.destinations,
                                      dayItinerary: updated.dayItinerary || [],
                                    }),
                                );
                                setPlan(updated);
                              } catch (err) {
                                console.error("Erro ao remover destino:", err);
                                alert("Erro ao remover destino");
                              } finally {
                                setSaving(false);
                              }
                            }}
                          >
                            <FaTrash />
                          </button>
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              )}

              <div
                style={{
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "space-between",
                }}
              >
                <h4>Itinerário</h4>
                <div>
                  <button
                    className="btn-secondary"
                    onClick={async () => {
                      if (!plan) return;
                      const newDay = {
                        title: `Dia ${(plan.dayItinerary?.length || 0) + 1}`,
                        activities: [],
                      };
                      const updated = {
                        ...plan,
                        dayItinerary: [...(plan.dayItinerary || []), newDay],
                      };
                      setSaving(true);
                      try {
                        await import("../../services/travelPlans").then((m) =>
                          m.updateTravelPlan(id, {
                            title: updated.title,
                            startDate: updated.startDate,
                            endDate: updated.endDate,
                            userId: updated.userId,
                            destinations: updated.destinations || [],
                            dayItinerary: updated.dayItinerary,
                          }),
                        );
                        setPlan(updated);
                      } catch (err) {
                        console.error("Erro ao adicionar dia:", err);
                        alert("Erro ao adicionar dia");
                      } finally {
                        setSaving(false);
                      }
                    }}
                  >
                    + Adicionar Dia
                  </button>
                </div>
              </div>

              {plan?.dayItinerary && plan.dayItinerary.length > 0 ? (
                plan.dayItinerary.map((day, idx) => (
                  <div className="day-card" key={idx}>
                    <div
                      style={{
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "space-between",
                      }}
                    >
                      <h4 style={{ margin: 0 }}>
                        Dia {idx + 1} — {dateForDay(idx)}{" "}
                        {day.title ? `- ${day.title}` : ""}
                      </h4>
                      <div>
                        <button
                          className="btn-icon"
                          title="Remover dia"
                          onClick={async () => {
                            if (!plan) return;
                            if (
                              !window.confirm("Remover este dia do itinerário?")
                            )
                              return;
                            const updated = {
                              ...plan,
                              dayItinerary: plan.dayItinerary.filter(
                                (_, di) => di !== idx,
                              ),
                            };
                            setSaving(true);
                            try {
                              await import("../../services/travelPlans").then(
                                (m) =>
                                  m.updateTravelPlan(id, {
                                    title: updated.title,
                                    startDate: updated.startDate,
                                    endDate: updated.endDate,
                                    userId: updated.userId,
                                    destinations: updated.destinations || [],
                                    dayItinerary: updated.dayItinerary,
                                  }),
                              );
                              setPlan(updated);
                            } catch (err) {
                              console.error("Erro ao remover dia:", err);
                              alert("Erro ao remover dia");
                            } finally {
                              setSaving(false);
                            }
                          }}
                        >
                          <FaTrash />
                        </button>
                      </div>
                    </div>
                    {day.activities &&
                      day.activities.map((act, aIdx) => (
                        <div className="activity-card" key={aIdx}>
                          <FaLandmark className="activity-icon" />
                          <p style={{ margin: 0, flex: 1 }}>{act}</p>
                          <button
                            className="btn-icon small"
                            title="Remover atividade"
                            onClick={async () => {
                              if (!plan) return;
                              if (!window.confirm("Remover esta atividade?"))
                                return;
                              const updated = { ...plan };
                              updated.dayItinerary = updated.dayItinerary.map(
                                (d, di) =>
                                  di === idx
                                    ? {
                                        ...d,
                                        activities: (d.activities || []).filter(
                                          (_, ai) => ai !== aIdx,
                                        ),
                                      }
                                    : d,
                              );
                              setSaving(true);
                              try {
                                await import("../../services/travelPlans").then(
                                  (m) =>
                                    m.updateTravelPlan(id, {
                                      title: updated.title,
                                      startDate: updated.startDate,
                                      endDate: updated.endDate,
                                      userId: updated.userId,
                                      destinations: updated.destinations || [],
                                      dayItinerary: updated.dayItinerary,
                                    }),
                                );
                                setPlan(updated);
                              } catch (err) {
                                console.error(
                                  "Erro ao remover atividade:",
                                  err,
                                );
                                alert("Erro ao remover atividade");
                              } finally {
                                setSaving(false);
                              }
                            }}
                          >
                            <FaTrash />
                          </button>
                        </div>
                      ))}
                    {addingActivityFor === idx ? (
                      <div style={{ display: "flex", gap: 8, marginTop: 8 }}>
                        <input
                          className="inline-input"
                          value={newActivityText}
                          onChange={(e) => setNewActivityText(e.target.value)}
                          placeholder="Descrição da atividade"
                        />
                        <button
                          className="btn-create"
                          onClick={async () => {
                            if (!newActivityText.trim()) return;
                            const updated = { ...plan };
                            updated.dayItinerary = updated.dayItinerary.map(
                              (d, di) =>
                                di === idx
                                  ? {
                                      ...d,
                                      activities: [
                                        ...(d.activities || []),
                                        newActivityText.trim(),
                                      ],
                                    }
                                  : d,
                            );
                            setSaving(true);
                            try {
                              await import("../../services/travelPlans").then(
                                (m) =>
                                  m.updateTravelPlan(id, {
                                    title: updated.title,
                                    startDate: updated.startDate,
                                    endDate: updated.endDate,
                                    userId: updated.userId,
                                    destinations: updated.destinations || [],
                                    dayItinerary: updated.dayItinerary,
                                  }),
                              );
                              setPlan(updated);
                              setNewActivityText("");
                              setAddingActivityFor(null);
                            } catch (err) {
                              console.error(
                                "Erro ao adicionar atividade:",
                                err,
                              );
                              alert("Erro ao adicionar atividade");
                            } finally {
                              setSaving(false);
                            }
                          }}
                        >
                          {saving ? "Adicionando..." : "Adicionar"}
                        </button>
                        <button
                          className="btn-secondary"
                          onClick={() => {
                            setAddingActivityFor(null);
                            setNewActivityText("");
                          }}
                        >
                          Cancelar
                        </button>
                      </div>
                    ) : (
                      <button
                        className="add-activity-btn"
                        onClick={() => setAddingActivityFor(idx)}
                      >
                        <FaPlus /> Adicionar Atividade
                      </button>
                    )}
                  </div>
                ))
              ) : (
                <p>
                  Sem itens no itinerário. Adicione destinos ou dias para
                  começar.
                </p>
              )}
            </>
          )}
        </div>

        <div className="map-column">
          {activeTab === "manual" && <TravelMap />}
          {activeTab === "iaDestino" && (
            <SugestaoDestino
              plan={plan}
              onAddDestination={async (newDest) => {
                const updated = {
                  ...plan,
                  destinations: [...(plan.destinations || []), newDest],
                };
                setPlan(updated);

                await travelPlanService.updateTravelPlan(plan.id, {
                  title: updated.title,
                  startDate: updated.startDate,
                  endDate: updated.endDate,
                  userId: updated.userId,
                  destinations: updated.destinations,
                  dayItinerary: updated.dayItinerary || [],
                });
              }}
              userId={user.id}
            />
          )}

          {activeTab === "ia" && (
            <div className="suggestion-panel">
              <div className="suggestion-controls-top">
                <div
                  style={{
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "space-between",
                    gap: 12,
                    width: "100%",
                  }}
                >
                  <div>
                    <h3 style={{ margin: 0 }}>Sugestão da IA</h3>
                    <p style={{ margin: "6px 0 0 0", fontSize: "0.95rem" }}>
                      Gere uma sugestão usando IA.
                    </p>
                  </div>
                  <div
                    style={{ display: "flex", gap: 8, alignItems: "center" }}
                  >
                    <button
                      className="btn-primary"
                      onClick={async () => {
                        setSugError(null);
                        setSugResult("");
                        if (!plan?.id) {
                          setSugError("Nenhum travel plan carregado.");
                          return;
                        }
                        setSugLoading(true);
                        try {
                          const resp = await getSuggestionByTravelPlan(plan.id);
                          setSugResult(resp.data || JSON.stringify(resp));
                        } catch (err) {
                          console.error("Erro sugestão (plan):", err);
                          setSugError(
                            err.response?.data ||
                              err.message ||
                              "Erro ao gerar sugestão por plano",
                          );
                        } finally {
                          setSugLoading(false);
                        }
                      }}
                      disabled={sugLoading}
                    >
                      {sugLoading ? "Gerando..." : "Gerar por travelPlan"}
                    </button>
                  </div>
                </div>
              </div>

              <div className="suggestion-result-box">
                {sugError && <div className="error">{String(sugError)}</div>}
                {sugResult ? (
                  <div className="result-card">
                    <pre className="result-pre">
                      {typeof sugResult === "string"
                        ? sugResult
                        : JSON.stringify(sugResult, null, 2)}
                    </pre>
                  </div>
                ) : (
                  <div className="hint">Nenhuma sugestão gerada ainda.</div>
                )}
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default ItineraryManager;
