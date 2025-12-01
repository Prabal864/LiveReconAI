const Stats = () => {
  const stats = [
    { value: "10K+", label: "Active Users" },
    { value: "5M+", label: "Transactions Analyzed" },
    { value: "99.9%", label: "Uptime" },
    { value: "50+", label: "Bank Integrations" },
  ];

  return (
    <section className="py-16 px-4 sm:px-6 lg:px-8 border-y border-gray-800">
      <div className="max-w-7xl mx-auto">
        <div className="grid grid-cols-2 md:grid-cols-4 gap-8">
          {stats.map((stat, index) => (
            <div key={index} className="text-center">
              <div className="text-4xl sm:text-5xl font-bold bg-gradient-to-r from-purple-500 to-cyan-400 bg-clip-text text-transparent">
                {stat.value}
              </div>
              <div className="text-gray-400 mt-2">{stat.label}</div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

export default Stats;
