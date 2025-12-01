const HowItWorks = () => {
  const steps = [
    {
      number: "01",
      title: "Connect Your Accounts",
      description: "Securely link your bank accounts through Setu Account Aggregator. Your consent controls everything.",
    },
    {
      number: "02",
      title: "Data Aggregation",
      description: "We fetch and organize your transaction data from all connected accounts in one place.",
    },
    {
      number: "03",
      title: "AI Analysis",
      description: "Our AI processes your data, categorizes spending, and identifies patterns and insights.",
    },
    {
      number: "04",
      title: "Ask Anything",
      description: "Chat with your financial data using natural language and get instant, intelligent answers.",
    },
  ];

  return (
    <section id="how-it-works" className="py-20 px-4 sm:px-6 lg:px-8 bg-gradient-to-b from-transparent via-gray-900/50 to-transparent">
      <div className="max-w-7xl mx-auto">
        <div className="text-center mb-16">
          <h2 className="text-3xl sm:text-4xl font-bold text-white mb-4">
            How it{" "}
            <span className="bg-gradient-to-r from-purple-500 to-cyan-400 bg-clip-text text-transparent">
              works
            </span>
          </h2>
          <p className="text-gray-400 text-lg max-w-2xl mx-auto">
            From connecting your accounts to getting insights, it's all seamless.
          </p>
        </div>
        
        <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-8">
          {steps.map((step, index) => (
            <div key={index} className="relative">
              <div className="text-6xl font-bold text-gray-800 mb-4">{step.number}</div>
              <h3 className="text-xl font-semibold text-white mb-2">{step.title}</h3>
              <p className="text-gray-400">{step.description}</p>
              {index < steps.length - 1 && (
                <div className="hidden lg:block absolute top-8 left-full w-full">
                  <svg className="w-full h-4 text-gray-700" viewBox="0 0 100 20">
                    <path
                      d="M0 10 L90 10 M85 5 L95 10 L85 15"
                      fill="none"
                      stroke="currentColor"
                      strokeWidth="2"
                      strokeDasharray="5,5"
                    />
                  </svg>
                </div>
              )}
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

export default HowItWorks;
