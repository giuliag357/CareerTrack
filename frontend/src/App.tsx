function App() {
  const appName = "CareerTrack";
  const today = new Date().toLocaleDateString("it-IT");

  return (
      <main>
        <h1>{appName}</h1>
        <p>Oggi è il {today}. Pronta a tracciare le tue candidature?</p>
      </main>
  );
}

export default App;