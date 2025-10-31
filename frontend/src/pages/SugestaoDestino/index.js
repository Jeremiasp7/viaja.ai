import React, { useState, useRef, useEffect } from "react";
import styled from "styled-components";
import { FaPaperPlane, FaPlus, FaMapMarkerAlt } from "react-icons/fa";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  background: var(--white);
  border-radius: 1rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  overflow: hidden;
`;

const ChatSection = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 0;
  max-height: 70vh; /* Limita a altura do chat para ~70% */
`;

const MessagesContainer = styled.div`
  flex: 1;
  overflow-y: auto;
  padding: 1.5rem;
  background: var(--light-gray);
  min-height: 0;
`;

const Message = styled.div`
  display: flex;
  justify-content: ${(props) => (props.isUser ? "flex-end" : "flex-start")};
  margin-bottom: 1rem;
`;

const MessageBubble = styled.div`
  max-width: 70%;
  padding: 1rem 1.5rem;
  border-radius: 1.5rem;
  background: ${(props) =>
    props.isUser ? "var(--primary-color)" : "var(--white)"};
  color: ${(props) => (props.isUser ? "var(--white)" : "var(--text-color)")};
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border: ${(props) => (!props.isUser ? "1px solid #e2e8f0" : "none")};
`;

const InputContainer = styled.div`
  display: flex;
  align-items: flex-end;
  padding: 1rem;
  border-top: 1px solid var(--light-gray);
  background: var(--white);
  flex-shrink: 0;
`;

const TextArea = styled.textarea`
  flex: 1;
  padding: 1rem 1.5rem;
  border-radius: 20px;
  border: none;
  font-size: 1rem;
  background: var(--light-gray);
  resize: none;
  overflow-y: auto;
  max-height: 100px;
  font-family: inherit;
  line-height: 1.5;

  &:focus {
    outline: none;
    background: var(--white);
    box-shadow: 0 0 0 2px var(--primary-color);
  }
`;

const SendButton = styled.button`
  background: var(--primary-color);
  color: var(--white);
  border: none;
  border-radius: 50%;
  width: 44px;
  height: 44px;
  padding: 0;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 0.75rem;
  flex-shrink: 0;

  &:hover {
    background: #350668;
  }

  &:disabled {
    background: #a5a5a5;
    cursor: not-allowed;
  }

  svg {
    width: 20px;
    height: 20px;
  }
`;

const RecommendationsSection = styled.section`
  flex: 1;
  overflow-y: auto;
  padding: 1.5rem;
  min-height: 30vh; /* Garante espaço mínimo para os cards */
`;

const SectionTitle = styled.h3`
  color: var(--text-color);
  margin-bottom: 1rem;
  font-size: 1.1rem;
`;

const CardsContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 1rem;
`;

const RecommendationCard = styled.div`
  background: var(--white);
  border-radius: 0.75rem;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition:
    transform 0.2s ease,
    box-shadow 0.2s ease;
  cursor: pointer;
  border: 1px solid #e2e8f0;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  }
`;

const CardInfo = styled.div`
  padding: 1.25rem;
`;

const CardHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 0.75rem;
`;

const LocationInfo = styled.div`
  flex: 1;
`;

const CityName = styled.h4`
  margin: 0;
  color: var(--text-color);
  font-size: 1.1rem;
`;

const CountryName = styled.p`
  margin: 0.25rem 0 0 0;
  color: #666;
  font-size: 0.85rem;
`;

const AddButton = styled.button`
  background: var(--secondary-color);
  color: var(--white);
  border: none;
  border-radius: 50px;
  padding: 0.5rem 1rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 0.85rem;
  font-weight: 600;

  &:hover {
    background: #e57a00;
  }

  &:disabled {
    background: #a5a5a5;
    cursor: not-allowed;
  }
`;

const PlacesList = styled.ul`
  list-style: none;
  padding: 0;
  margin: 0.75rem 0 0 0;
`;

const PlaceItem = styled.li`
  display: flex;
  align-items: flex-start;
  padding: 0.5rem 0;
  border-bottom: 1px solid var(--light-gray);
  font-size: 0.85rem;

  &:last-child {
    border-bottom: none;
  }
`;

const PlaceIcon = styled(FaMapMarkerAlt)`
  color: var(--secondary-color);
  margin-right: 0.5rem;
  margin-top: 0.15rem;
  flex-shrink: 0;
  font-size: 0.8rem;
`;

const PlaceInfo = styled.div`
  flex: 1;
`;

const PlaceName = styled.strong`
  color: var(--text-color);
  display: block;
  margin-bottom: 0.15rem;
`;

const Coordinates = styled.div`
  font-size: 0.75rem;
  color: #666;
`;

const LoadingDots = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.25rem;
  padding: 1rem;
`;

const Dot = styled.div`
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: var(--primary-color);
  animation: bounce 1.4s infinite ease-in-out;

  &:nth-child(1) {
    animation-delay: -0.32s;
  }
  &:nth-child(2) {
    animation-delay: -0.16s;
  }

  @keyframes bounce {
    0%,
    80%,
    100% {
      transform: scale(0.8);
      opacity: 0.5;
    }
    40% {
      transform: scale(1);
      opacity: 1;
    }
  }
`;

const SuccessMessage = styled.div`
  background: #d4edda;
  color: #155724;
  padding: 0.5rem 0.75rem;
  border-radius: 0.375rem;
  margin: 0.5rem 0;
  border: 1px solid #c3e6cb;
  font-size: 0.85rem;
`;

const EmptyState = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  color: #666;
  text-align: center;

  p {
    margin: 0.5rem 0 0 0;
    font-size: 0.9rem;
  }
`;

const RecommendationChat = ({ plan, onAddDestination, userId, api }) => {
  const [messages, setMessages] = useState([]);
  const [inputMessage, setInputMessage] = useState("");
  const [recommendations, setRecommendations] = useState([]);
  const [isLoading, setLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [saving, setSaving] = useState(false);
  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const handleSendMessage = async (e) => {
    e.preventDefault();
    if (!inputMessage.trim() || isLoading) return;

    const userMessage = {
      id: Date.now(),
      text: inputMessage,
      isUser: true,
      timestamp: new Date(),
    };

    setMessages((prev) => [...prev, userMessage]);
    setInputMessage("");
    setLoading(true);

    try {
      const token = localStorage.getItem("token");
      const response = await api.post(
        `/recomendacoes/${userId}`,
        {
          userPrompt: userMessage.text,
        },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        },
      );

      if (response.status === 200) {
        const data = response.data;

        const botMessage = {
          id: Date.now() + 1,
          text: "Aqui estão algumas recomendações baseadas no seu histórico de viagens!",
          isUser: false,
          timestamp: new Date(),
        };

        setMessages((prev) => [...prev, botMessage]);
        setRecommendations(data);
      } else {
        throw new Error("Failed to get recommendations");
      }
    } catch (error) {
      console.error("Error getting recommendations:", error);
      const errorMessage = {
        id: Date.now() + 1,
        text: "Desculpe, ocorreu um erro ao buscar recomendações. Tente novamente.",
        isUser: false,
        timestamp: new Date(),
      };
      setMessages((prev) => [...prev, errorMessage]);
    } finally {
      setLoading(false);
    }
  };

  const handleAddToTravelPlan = async (destination) => {
    if (!plan) {
      alert("Nenhum plano de viagem encontrado.");
      return;
    }

    setSaving(true);
    try {
      const newDest = {
        country: destination.country,
        city: destination.city,
        arrivalDate: new Date().toISOString().split("T")[0],
        departureDate: new Date(Date.now() + 3 * 24 * 60 * 60 * 1000)
          .toISOString()
          .split("T")[0],
      };

      if (onAddDestination) {
        await onAddDestination(newDest);
        setSuccessMessage(
          `${destination.city}, ${destination.country} foi adicionado ao seu plano!`,
        );
        setTimeout(() => setSuccessMessage(""), 3000);
      }
    } catch (error) {
      console.error("Erro ao adicionar destino:", error);
      alert("Erro ao adicionar destino ao plano de viagem.");
    } finally {
      setSaving(false);
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault();
      handleSendMessage(e);
    }
  };

  return (
    <Container>
      {successMessage && <SuccessMessage>{successMessage}</SuccessMessage>}

      {/* Seção do Chat (ocupa ~70% do espaço) */}
      <ChatSection>
        <MessagesContainer>
          {messages.length === 0 && (
            <Message isUser={false}>
              <MessageBubble isUser={false}>
                Olá! Sou seu assistente de viagens. Pergunte-me sobre destinos
                ou peça recomendações personalizadas!
              </MessageBubble>
            </Message>
          )}
          {messages.map((message) => (
            <Message key={message.id} isUser={message.isUser}>
              <MessageBubble isUser={message.isUser}>
                {message.text}
              </MessageBubble>
            </Message>
          ))}
          {isLoading && (
            <Message isUser={false}>
              <MessageBubble isUser={false}>
                <LoadingDots>
                  <Dot />
                  <Dot />
                  <Dot />
                </LoadingDots>
              </MessageBubble>
            </Message>
          )}
          <div ref={messagesEndRef} />
        </MessagesContainer>
        <InputContainer>
          <form
            onSubmit={handleSendMessage}
            style={{ display: "flex", width: "100%", alignItems: "flex-end" }}
          >
            <TextArea
              rows="1"
              value={inputMessage}
              onChange={(e) => setInputMessage(e.target.value)}
              onKeyDown={handleKeyDown}
              placeholder="Pergunte sobre destinos para sua próxima viagem..."
              disabled={isLoading}
              onInput={(e) => {
                e.target.style.height = "auto";
                e.target.style.height = `${e.target.scrollHeight}px`;
              }}
            />
            <SendButton type="submit" disabled={isLoading}>
              <FaPaperPlane />
            </SendButton>
          </form>
        </InputContainer>
      </ChatSection>

      {/* Seção de Recomendações (ocupa o espaço restante ~30%) */}
      <RecommendationsSection>
        {recommendations.length > 0 ? (
          <>
            <SectionTitle>Destinos Recomendados</SectionTitle>
            <CardsContainer>
              {recommendations.map((destination, index) => (
                <RecommendationCard key={index}>
                  <CardInfo>
                    <CardHeader>
                      <LocationInfo>
                        <CityName>{destination.city}</CityName>
                        <CountryName>{destination.country}</CountryName>
                      </LocationInfo>
                      <AddButton
                        onClick={() => handleAddToTravelPlan(destination)}
                        disabled={saving}
                      >
                        <FaPlus />
                        {saving ? "Adicionando..." : "Adicionar"}
                      </AddButton>
                    </CardHeader>

                    <div>
                      <h5
                        style={{
                          margin: "0.75rem 0 0.25rem 0",
                          color: "var(--text-color)",
                          fontSize: "0.9rem",
                        }}
                      >
                        Locais para visitar:
                      </h5>
                      <PlacesList>
                        {destination.mustVisitPlaces.map(
                          (place, placeIndex) => (
                            <PlaceItem key={placeIndex}>
                              <PlaceIcon />
                              <PlaceInfo>
                                <PlaceName>{place.name}</PlaceName>
                                <Coordinates>
                                  Lat: {place.latitude}, Lng: {place.longitude}
                                </Coordinates>
                              </PlaceInfo>
                            </PlaceItem>
                          ),
                        )}
                      </PlacesList>
                    </div>
                  </CardInfo>
                </RecommendationCard>
              ))}
            </CardsContainer>
          </>
        ) : (
          <EmptyState>
            <p>Faça uma pergunta para ver recomendações de destinos</p>
          </EmptyState>
        )}
      </RecommendationsSection>
    </Container>
  );
};

export default RecommendationChat;
