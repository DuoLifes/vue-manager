import defaultSettings from '@/settings'

const title = defaultSettings.title || '城市生命线'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
