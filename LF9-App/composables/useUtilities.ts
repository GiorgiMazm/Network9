export const useUtilities = () => {
  function toTitleCase(str: string) {
    if (!str) return;
    return str.replace(/\w\S*/g, function (txt) {
      return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
    });
  }

  return {
    toTitleCase,
  };
};
