import { useState } from "react";

import InvoiceForm from "./components/InvoiceForm";
import InvoiceHistory from "./components/InvoiceHistory";

function App() {

  const [refreshHistory, setRefreshHistory] =
    useState(false);

  const triggerRefresh = () => {
    setRefreshHistory(prev => !prev);
  };

  return (
    <div className="container">

      <InvoiceForm
        onInvoiceCreated={triggerRefresh}
      />

      <hr />

      <InvoiceHistory
        refreshHistory={refreshHistory}
      />

    </div>
  );
}

export default App;